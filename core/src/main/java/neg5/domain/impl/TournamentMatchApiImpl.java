package neg5.domain.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchTeamApi;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentMatchPhaseApi;
import neg5.domain.api.TournamentMatchPhaseDTO;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.impl.dataAccess.TournamentMatchDAO;
import neg5.domain.impl.entities.TournamentMatch;
import neg5.domain.impl.entities.transformers.data.Match;
import neg5.domain.impl.mappers.TournamentMatchMapper;
import neg5.domain.impl.mappers.data.MatchToMatchDTOMapper;
import neg5.domain.impl.matchValidators.MatchValidationContext;
import neg5.domain.impl.matchValidators.MatchValidatorsChain;

@Singleton
public class TournamentMatchApiImpl
        extends AbstractApiLayerImpl<TournamentMatch, TournamentMatchDTO, String>
        implements TournamentMatchApi {

    private final TournamentTossupValueApi tournamentTossupValueManager;
    private final MatchTeamApi matchTeamManager;
    private final TournamentTeamApi tournamentTeamApi;
    private final TournamentMatchPhaseApi matchPhaseManager;
    private final TournamentRulesApi tournamentRulesApi;
    private final MatchValidatorsChain matchValidatorsChain;

    private final TournamentMatchMapper tournamentMatchMapper;
    private final MatchToMatchDTOMapper matchToMatchDTOMapper;
    private final TournamentMatchDAO rwTournamentMatchDAO;

    @Inject
    public TournamentMatchApiImpl(
            TournamentTossupValueApi tournamentTossupValueManager,
            MatchTeamApi matchTeamManager,
            TournamentTeamApi tournamentTeamApi,
            TournamentMatchPhaseApi matchPhaseManager,
            TournamentRulesApi tournamentRulesApi,
            MatchValidatorsChain matchValidatorsChain,
            TournamentMatchMapper tournamentMatchMapper,
            MatchToMatchDTOMapper matchToMatchDTOMapper,
            TournamentMatchDAO rwTournamentMatchDAO) {
        this.tournamentTossupValueManager = tournamentTossupValueManager;
        this.matchTeamManager = matchTeamManager;
        this.tournamentTeamApi = tournamentTeamApi;
        this.matchPhaseManager = matchPhaseManager;
        this.tournamentRulesApi = tournamentRulesApi;
        this.matchValidatorsChain = matchValidatorsChain;
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
    public TournamentMatchDTO create(@Nonnull TournamentMatchDTO match) {
        Set<String> phases = match.getPhases();
        TournamentMatchDTO createdMatch = super.create(match);
        createdMatch.setTeams(
                match.getTeams().stream()
                        .map(
                                matchTeam -> {
                                    matchTeam.setTournamentId(match.getTournamentId());
                                    matchTeam.setMatchId(createdMatch.getId());
                                    return matchTeamManager.create(matchTeam);
                                })
                        .collect(Collectors.toSet()));
        createdMatch.setPhases(
                phases == null ? new HashSet<>() : associateMatchWithPhases(createdMatch, phases));

        return createdMatch;
    }

    @Override
    @Transactional
    public TournamentMatchDTO update(@Nonnull TournamentMatchDTO tournamentMatchDTO) {
        // TODO This creates a new match with a new ID. Come back and preserve the ID
        TournamentMatchDTO original = get(tournamentMatchDTO.getId());
        tournamentMatchDTO.setTournamentId(original.getTournamentId());
        delete(tournamentMatchDTO.getId());
        getDao().flush();
        tournamentMatchDTO.setId(null);
        return create(tournamentMatchDTO);
    }

    @Override
    public List<TournamentMatchDTO> findAllByTournamentId(@Nonnull String tournamentId) {
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
    public List<TournamentMatchDTO> findAllByTournamentAndPhase(
            String tournamentId, String phaseId) {
        return findAllByTournamentId(tournamentId).stream()
                .filter(match -> phaseId == null || match.getPhases().contains(phaseId))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<TournamentMatchDTO>> groupMatchesByTeams(
            String tournamentId, String phaseId) {
        List<TournamentMatchDTO> matches = findAllByTournamentAndPhase(tournamentId, phaseId);
        Map<String, List<TournamentMatchDTO>> matchesByTeamId = new HashMap<>();
        matches.forEach(
                match -> {
                    Set<MatchTeamDTO> teams = match.getTeams();
                    teams.forEach(
                            team -> {
                                matchesByTeamId.computeIfPresent(
                                        team.getTeamId(),
                                        (id, list) -> {
                                            list.add(match);
                                            return list;
                                        });
                                matchesByTeamId.computeIfAbsent(
                                        team.getTeamId(), teamId -> Lists.newArrayList(match));
                            });
                });
        List<TournamentTeamDTO> allTeams = tournamentTeamApi.findAllByTournamentId(tournamentId);
        allTeams.forEach(team -> matchesByTeamId.putIfAbsent(team.getId(), new ArrayList<>()));
        return matchesByTeamId;
    }

    @Transactional
    protected List<Match> findByRawQuery(String tournamentId) {
        return getDao().findMatchesByTournamentIdWithRawQuery(tournamentId);
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentMatchDTO dto) {
        MatchValidationContext validationContext = getValidationContext(dto);
        return Optional.of(matchValidatorsChain.runValidations(validationContext));
    }

    private Set<String> associateMatchWithPhases(TournamentMatchDTO match, Set<String> phases) {
        return matchPhaseManager
                .associateMatchWithPhases(phases, match.getId(), match.getTournamentId()).stream()
                .map(TournamentMatchPhaseDTO::getPhaseId)
                .collect(Collectors.toSet());
    }

    private Map<Integer, TournamentTossupValueDTO> getTossupValueMap(String tournamentId) {
        return tournamentTossupValueManager.findAllByTournamentId(tournamentId).stream()
                .collect(Collectors.toMap(TournamentTossupValueDTO::getValue, Function.identity()));
    }

    private MatchValidationContext getValidationContext(TournamentMatchDTO subject) {
        List<TournamentMatchDTO> allMatches = findAllByTournamentId(subject.getTournamentId());
        if (subject.getTournamentId() == null) {
            return new MatchValidationContext(allMatches, subject, null, null, null);
        }
        List<TournamentTeamDTO> teams =
                tournamentTeamApi.findAllByTournamentId(subject.getTournamentId());
        Map<String, TournamentTeamDTO> teamsById =
                teams.stream().collect(Collectors.toMap(TournamentTeamDTO::getId, team -> team));
        Map<String, TournamentPlayerDTO> playersById =
                teams.stream()
                        .filter(team -> team.getPlayers() != null)
                        .flatMap(team -> team.getPlayers().stream())
                        .collect(Collectors.toMap(TournamentPlayerDTO::getId, player -> player));
        return new MatchValidationContext(
                allMatches,
                subject,
                tournamentRulesApi.getForTournament(subject.getTournamentId()),
                teamsById,
                playersById);
    }
}
