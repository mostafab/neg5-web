package neg5.domain.impl;

import static neg5.validation.FieldValidation.requireCondition;
import static neg5.validation.FieldValidation.requireCustomValidation;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPhaseDTO;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.api.UpdateTournamentRequestDTO;
import neg5.domain.impl.dataAccess.TournamentDAO;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.mappers.TournamentMapper;
import neg5.domain.impl.mappers.UpdateTournamentRequestMapper;
import neg5.userData.CurrentUserContext;
import neg5.userData.UserData;
import neg5.validation.ObjectValidationException;
import org.apache.commons.collections.CollectionUtils;

@Singleton
public class TournamentApiImpl extends AbstractApiLayerImpl<Tournament, TournamentDTO, String>
        implements TournamentApi {

    private final TournamentDAO rwTournamentDAO;
    private final TournamentMapper tournamentMapper;
    private final CurrentUserContext currentUserContext;

    private final TournamentPhaseApi phaseManager;
    private final TournamentTossupValueApi tossupValueManager;
    private final TournamentMatchApi matchManager;
    private final UpdateTournamentRequestMapper updateTournamentRequestMapper;

    @Inject
    public TournamentApiImpl(
            TournamentDAO rwTournamentDAO,
            TournamentMapper tournamentMapper,
            CurrentUserContext currentUserContext,
            TournamentPhaseApi phaseManager,
            TournamentTossupValueApi tossupValueManager,
            TournamentMatchApi matchManager,
            UpdateTournamentRequestMapper updateTournamentRequestMapper) {
        this.rwTournamentDAO = rwTournamentDAO;
        this.tournamentMapper = tournamentMapper;
        this.currentUserContext = currentUserContext;
        this.phaseManager = phaseManager;
        this.tossupValueManager = tossupValueManager;
        this.matchManager = matchManager;
        this.updateTournamentRequestMapper = updateTournamentRequestMapper;
    }

    @Transactional
    @Override
    public TournamentDTO create(@Nonnull TournamentDTO tournament) {
        UserData currentUser = currentUserContext.getUserDataOrThrow();
        tournament.setDirectorId(currentUser.getUsername());

        TournamentDTO createdTournament = super.create(tournament);
        createdTournament.setPhases(createPhases(createdTournament.getId(), tournament));
        createdTournament.setTossupValues(
                createTossupValues(createdTournament.getId(), tournament));

        return createdTournament;
    }

    @Transactional
    public TournamentDTO update(
            String tournamentId, UpdateTournamentRequestDTO updateTournamentRequest) {
        TournamentDTO dto =
                updateTournamentRequestMapper.mergeToEntity(
                        updateTournamentRequest, get(tournamentId));
        return update(dto);
    }

    @Transactional
    public List<TournamentDTO> getTournamentsOwnedByUser(String userId) {
        return getDao().getTournamentsOwnedByUser(userId).stream()
                .map(tournamentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected TournamentDAO getDao() {
        return rwTournamentDAO;
    }

    @Override
    protected TournamentMapper getMapper() {
        return tournamentMapper;
    }

    @Override
    protected String getIdFromDTO(TournamentDTO tournamentDTO) {
        return tournamentDTO.getId();
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentDTO tournamentDTO) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, tournamentDTO.getName(), "name");
        requireNotNull(errors, tournamentDTO.getBonusPointValue(), "bonusPointValue");
        requireNotNull(errors, tournamentDTO.getPartsPerBonus(), "partsPerBonus");
        requireNotNull(
                errors, tournamentDTO.getMaxActivePlayersPerTeam(), "maxActivePlayersPerTeam");

        requireCondition(
                errors,
                tournamentDTO.getBonusPointValue() == null
                        || tournamentDTO.getBonusPointValue() > 0,
                "bonusPointValue",
                "bonusPointValue must be positive.");
        requireCondition(
                errors,
                tournamentDTO.getPartsPerBonus() == null || tournamentDTO.getPartsPerBonus() >= 0,
                "partsPerBonus",
                "partsPerBonus must be non-negative");
        requireCondition(
                errors,
                tournamentDTO.getMaxActivePlayersPerTeam() == null
                        || tournamentDTO.getMaxActivePlayersPerTeam() > 0,
                "maxActivePlayersPerTeam",
                "maxActivePlayersPerTeam must be positive.");
        requireCustomValidation(
                errors, () -> ensureAllUniqueTossupValueTypes(tournamentDTO.getTossupValues()));

        return Optional.of(errors);
    }

    private void ensureAllUniqueTossupValueTypes(Set<TournamentTossupValueDTO> tossupValues) {
        if (tossupValues == null) {
            return;
        }
        int uniqueNumberOfTossupValues =
                tossupValues.stream()
                        .map(TournamentTossupValueDTO::getValue)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet())
                        .size();
        if (tossupValues.size() != uniqueNumberOfTossupValues) {
            throw new ObjectValidationException(
                    new FieldValidationErrors()
                            .add("tossupValues", "Each tossup value must be unique."));
        }
    }

    private void validateAllUniqueValues(List<TournamentTossupValueDTO> tossupValues) {
        Set<Integer> values =
                tossupValues.stream()
                        .map(TournamentTossupValueDTO::getValue)
                        .collect(Collectors.toSet());

        if (values.size() < tossupValues.size()) {
            throw new ObjectValidationException(
                    new FieldValidationErrors()
                            .add("value", "All unique tossup values required. "));
        }
    }

    private void throwIfAnyMatchesExist(String tournamentId) {
        Set<String> matchIds = matchManager.getMatchIdsByTournament(tournamentId);
        if (!matchIds.isEmpty()) {
            throw new ObjectValidationException(
                    new FieldValidationErrors()
                            .add(
                                    "matches",
                                    "Cannot update tossup value scheme for a tournament with existing matches."));
        }
    }

    private Set<TournamentTossupValueDTO> createTossupValues(
            String tournamentId, TournamentDTO tournament) {
        Set<TournamentTossupValueDTO> tossupValues =
                CollectionUtils.isEmpty(tournament.getTossupValues())
                        ? tossupValueManager.getDefaultTournamentValues()
                        : tournament.getTossupValues();

        return tossupValues.stream()
                .map(
                        tv -> {
                            tv.setTournamentId(tournamentId);
                            return tossupValueManager.create(tv);
                        })
                .collect(Collectors.toSet());
    }

    private Set<TournamentPhaseDTO> createPhases(String tournamentId, TournamentDTO tournament) {
        if (CollectionUtils.isEmpty(tournament.getPhases())) {
            return Sets.newHashSet(phaseManager.createDefaultPhase(tournamentId));
        }
        return tournament.getPhases().stream()
                .map(
                        phase -> {
                            phase.setTournamentId(tournamentId);
                            return phaseManager.create(phase);
                        })
                .collect(Collectors.toSet());
    }
}
