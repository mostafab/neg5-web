package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import neg5.api.TournamentMatchApi;
import org.neg5.FieldValidationErrors;
import org.neg5.TournamentMatchDTO;
import org.neg5.TournamentMatchPhaseDTO;
import org.neg5.TournamentTossupValueDTO;
import org.neg5.daos.TournamentMatchDAO;
import org.neg5.data.TournamentMatch;
import org.neg5.data.transformers.data.Match;
import org.neg5.mappers.TournamentMatchMapper;
import org.neg5.mappers.data.MatchToMatchDTOMapper;
import org.neg5.matchValidators.EnhancedMatchValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.neg5.validation.FieldValidation.requireCondition;
import static org.neg5.validation.FieldValidation.requireNotNull;

@Singleton
public class TournamentMatchManager
        extends AbstractDTOManager<TournamentMatch, TournamentMatchDTO, String> implements TournamentMatchApi {

    private final TournamentTossupValueManager tournamentTossupValueManager;
    private final MatchTeamManager matchTeamManager;
    private final TournamentMatchPhaseManager matchPhaseManager;
    private final Set<EnhancedMatchValidator> matchValidators;

    private final TournamentMatchMapper tournamentMatchMapper;
    private final MatchToMatchDTOMapper matchToMatchDTOMapper;
    private final TournamentMatchDAO rwTournamentMatchDAO;

    @Inject
    public TournamentMatchManager(TournamentTossupValueManager tournamentTossupValueManager,
                                  MatchTeamManager matchTeamManager,
                                  TournamentMatchPhaseManager matchPhaseManager,
                                  Set<EnhancedMatchValidator> matchValidators,
                                  TournamentMatchMapper tournamentMatchMapper,
                                  MatchToMatchDTOMapper matchToMatchDTOMapper,
                                  TournamentMatchDAO rwTournamentMatchDAO) {
        this.tournamentTossupValueManager = tournamentTossupValueManager;
        this.matchTeamManager = matchTeamManager;
        this.matchPhaseManager = matchPhaseManager;
        this.matchValidators = matchValidators;
        this.tournamentMatchMapper = tournamentMatchMapper;
        this.matchToMatchDTOMapper = matchToMatchDTOMapper;
        this.rwTournamentMatchDAO = rwTournamentMatchDAO;
    }

    @Override
    protected TournamentMatchMapper getMapper() {
        return tournamentMatchMapper;
    }

    @Override
    protected TournamentMatchDAO getDao() {
        return rwTournamentMatchDAO;
    }

    @Override
    protected String getIdFromDTO(TournamentMatchDTO tournamentMatchDTO) {
        return tournamentMatchDTO.getId();
    }

    @Override
    @Transactional
    public TournamentMatchDTO create(TournamentMatchDTO match) {
        Set<String> phases = match.getPhases();
        match.setPhases(null);
        TournamentMatchDTO createdMatch = super.create(match);
        createdMatch.setTeams(match.getTeams().stream()
                .map(matchTeam -> {
                    matchTeam.setTournamentId(match.getTournamentId());
                    matchTeam.setMatchId(createdMatch.getId());
                    return matchTeamManager.create(matchTeam);
                })
                .collect(Collectors.toSet()));
        createdMatch.setPhases(phases == null
            ? new HashSet<>()
            : associateMatchWithPhases(createdMatch, phases)
        );

        return createdMatch;
    }

    @Override
    @Transactional
    public TournamentMatchDTO update(TournamentMatchDTO tournamentMatchDTO) {
        // TODO This creates a new match with a new ID. Come back and preserve the ID
        TournamentMatchDTO original = get(tournamentMatchDTO.getId());
        tournamentMatchDTO.setTournamentId(original.getTournamentId());
        delete(tournamentMatchDTO.getId());
        getDao().flush();
        return create(tournamentMatchDTO);
    }

    @Override
    public List<TournamentMatchDTO> findAllByTournamentId(String tournamentId) {
        Map<Integer, TournamentTossupValueDTO> tossupValues = getTossupValueMap(tournamentId);
        return findByRawQuery(tournamentId).stream()
                .map(match -> matchToMatchDTOMapper.toDTO(match, tossupValues))
                .collect(Collectors.toList());
    }

    @Transactional
    public Set<String> getMatchIdsByTournament(String tournamentId) {
        return new HashSet<>(rwTournamentMatchDAO.findMatchIdsByTournament(tournamentId));
    }

    @Transactional
    public List<TournamentMatchDTO> findAllByTournamentAndPhase(String tournamentId, String phaseId) {
        return findAllByTournamentId(tournamentId).stream()
                .filter(match -> phaseId == null || match.getPhases().contains(phaseId))
                .collect(Collectors.toList());
    }

    @Transactional
    protected List<Match> findByRawQuery(String tournamentId) {
        return getDao().findMatchesByTournamentIdWithRawQuery(tournamentId);
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentMatchDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        // Basic validation
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNotNull(errors, dto.getRound(), "round");
        requireNotNull(errors, dto.getTossupsHeard(), "tossupsHeard");
        requireNotNull(errors, dto.getTeams(), "teams");
        requireCondition(errors, dto.getRound() == null || dto.getRound() > 0, "round", "Round must be greater than 0");
        requireCondition(errors, dto.getTossupsHeard() == null || dto.getTossupsHeard() > 0, "tossupsHeard", "Tossups Heard must be greater than 0");
        // Run through all the enhanced match validators
        List<TournamentMatchDTO> allMatches = findAllByTournamentId(dto.getTournamentId());
        matchValidators.forEach(validator -> Optional.ofNullable(validator.getErrors(allMatches, dto)).ifPresent(err -> errors.getErrors().addAll(err.getErrors())));
        return Optional.of(errors);
    }

    private Set<String> associateMatchWithPhases(TournamentMatchDTO match, Set<String> phases) {
        return matchPhaseManager.associateMatchWithPhases(phases, match.getId(), match.getTournamentId())
                .stream()
                .map(TournamentMatchPhaseDTO::getPhaseId)
                .collect(Collectors.toSet());
    }

    private Map<Integer, TournamentTossupValueDTO> getTossupValueMap(String tournamentId) {
        return tournamentTossupValueManager.findAllByTournamentId(tournamentId).stream()
                .collect(Collectors.toMap(TournamentTossupValueDTO::getValue, Function.identity()));
    }
}
