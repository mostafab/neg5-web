package neg5.domain.impl;

import static neg5.validation.FieldValidation.requireNotNull;
import static neg5.validation.ValidationUtilities.namesAreTheSame;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPhaseDTO;
import neg5.domain.api.TournamentPoolApi;
import neg5.domain.api.TournamentPoolDTO;
import neg5.domain.impl.dataAccess.TournamentPoolDAO;
import neg5.domain.impl.entities.TournamentPool;
import neg5.domain.impl.mappers.TournamentPoolMapper;

public class TournamentPoolApiImpl
        extends AbstractApiLayerImpl<TournamentPool, TournamentPoolDTO, String>
        implements TournamentPoolApi {

    private final TournamentPoolMapper mapper;
    private final TournamentPoolDAO divisionDAO;

    private final TournamentPhaseApi phaseManager;

    @Inject
    public TournamentPoolApiImpl(
            TournamentPoolMapper mapper,
            TournamentPoolDAO divisionDAO,
            TournamentPhaseApi phaseManager) {
        this.mapper = mapper;
        this.divisionDAO = divisionDAO;
        this.phaseManager = phaseManager;
    }

    @Override
    @Transactional
    public TournamentPoolDTO create(@Nonnull TournamentPoolDTO tournamentPoolDTO) {
        TournamentPhaseDTO phase = phaseManager.get(tournamentPoolDTO.getPhaseId());
        tournamentPoolDTO.setTournamentId(phase.getTournamentId());
        return super.create(tournamentPoolDTO);
    }

    @Override
    @Transactional
    public TournamentPoolDTO update(@Nonnull TournamentPoolDTO tournamentPoolDTO) {
        TournamentPoolDTO original = get(tournamentPoolDTO.getId());
        tournamentPoolDTO.setTournamentId(original.getTournamentId());
        tournamentPoolDTO.setPhaseId(original.getPhaseId());
        return super.update(tournamentPoolDTO);
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentPoolDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNotNull(errors, dto.getName(), "name");
        requireNotNull(errors, dto.getPhaseId(), "phaseId");
        validateUniquePoolName(errors, dto);
        return Optional.of(errors);
    }

    @Override
    protected TournamentPoolDAO getDao() {
        return divisionDAO;
    }

    @Override
    protected TournamentPoolMapper getMapper() {
        return mapper;
    }

    private void validateUniquePoolName(FieldValidationErrors errors, TournamentPoolDTO subject) {
        if (subject.getTournamentId() == null || subject.getName() == null) {
            return;
        }
        findAllByTournamentId(subject.getTournamentId()).stream()
                .filter(pool -> !pool.getId().equals(subject.getId()))
                .filter(pool -> namesAreTheSame(pool.getName(), subject.getName()))
                .findAny()
                .ifPresent(
                        match ->
                                errors.add(
                                        "name",
                                        String.format(
                                                "A pool named %s already exists for this tournament.",
                                                match.getName())));
    }
}
