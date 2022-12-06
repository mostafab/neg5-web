package neg5.domain.impl;

import static neg5.validation.FieldValidation.requireCustomValidation;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPhaseDTO;
import neg5.domain.impl.dataAccess.TournamentPhaseDAO;
import neg5.domain.impl.entities.TournamentPhase;
import neg5.domain.impl.mappers.TournamentPhaseMapper;
import neg5.validation.ObjectValidationException;

public class TournamentPhaseApiImpl
        extends AbstractApiLayerImpl<TournamentPhase, TournamentPhaseDTO, String>
        implements TournamentPhaseApi {

    private final TournamentPhaseDAO rwTournamentPhaseDAO;
    private final TournamentPhaseMapper tournamentPhaseMapper;

    @Inject
    public TournamentPhaseApiImpl(
            TournamentPhaseDAO rwTournamentPhaseDAO, TournamentPhaseMapper tournamentPhaseMapper) {
        this.rwTournamentPhaseDAO = rwTournamentPhaseDAO;
        this.tournamentPhaseMapper = tournamentPhaseMapper;
    }

    @Override
    @Transactional
    public TournamentPhaseDTO create(@Nonnull TournamentPhaseDTO tournamentPhaseDTO) {
        return super.create(tournamentPhaseDTO);
    }

    @Transactional
    public TournamentPhaseDTO createDefaultPhase(String tournamentId) {
        TournamentPhaseDTO phaseDTO = new TournamentPhaseDTO();
        phaseDTO.setTournamentId(tournamentId);
        phaseDTO.setName("Prelims");
        return create(phaseDTO);
    }

    @Override
    @Transactional
    public TournamentPhaseDTO update(@Nonnull TournamentPhaseDTO tournamentPhaseDTO) {
        TournamentPhaseDTO original = get(tournamentPhaseDTO.getId());
        tournamentPhaseDTO.setTournamentId(original.getTournamentId());
        return super.update(tournamentPhaseDTO);
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentPhaseDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNotNull(errors, dto.getName(), "name");
        requireCustomValidation(errors, () -> ensureUniquePhaseName(dto));
        return Optional.of(errors);
    }

    private void ensureUniquePhaseName(TournamentPhaseDTO tournamentPhase) {
        List<TournamentPhaseDTO> existingPhases =
                findAllByTournamentId(tournamentPhase.getTournamentId());
        String name = tournamentPhase.getName().toLowerCase().trim();
        for (TournamentPhaseDTO phase : existingPhases) {
            String phaseName = phase.getName().toLowerCase().trim();
            if (name.equals(phaseName) && !phase.getId().equals(tournamentPhase.getId())) {
                throw new ObjectValidationException(
                        new FieldValidationErrors()
                                .add(
                                        "name",
                                        "This tournament already has a phase with that name."));
            }
        }
    }

    @Override
    protected TournamentPhaseDAO getDao() {
        return rwTournamentPhaseDAO;
    }

    @Override
    protected TournamentPhaseMapper getMapper() {
        return tournamentPhaseMapper;
    }

    @Override
    protected String getIdFromDTO(TournamentPhaseDTO tournamentPhaseDTO) {
        return tournamentPhaseDTO.getId();
    }
}
