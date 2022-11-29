package neg5.api.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import neg5.api.TournamentPhaseApi;
import org.neg5.FieldValidationErrors;
import org.neg5.TournamentPhaseDTO;

import org.neg5.daos.TournamentPhaseDAO;
import org.neg5.data.TournamentPhase;
import org.neg5.mappers.TournamentPhaseMapper;
import org.neg5.validation.ObjectValidationException;

import java.util.List;
import java.util.Optional;

import static org.neg5.validation.FieldValidation.requireCustomValidation;
import static org.neg5.validation.FieldValidation.requireNotNull;

public class TournamentPhaseApiImpl extends AbstractApiLayerImpl<TournamentPhase, TournamentPhaseDTO, String>
        implements TournamentPhaseApi {

    private final TournamentPhaseDAO rwTournamentPhaseDAO;
    private final TournamentPhaseMapper tournamentPhaseMapper;

    @Inject
    public TournamentPhaseApiImpl(TournamentPhaseDAO rwTournamentPhaseDAO,
                                  TournamentPhaseMapper tournamentPhaseMapper) {
        this.rwTournamentPhaseDAO = rwTournamentPhaseDAO;
        this.tournamentPhaseMapper = tournamentPhaseMapper;
    }

    @Override
    @Transactional
    public TournamentPhaseDTO create(TournamentPhaseDTO tournamentPhaseDTO) {
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
    public TournamentPhaseDTO update(TournamentPhaseDTO tournamentPhaseDTO) {
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
        List<TournamentPhaseDTO> existingPhases = findAllByTournamentId(tournamentPhase.getTournamentId());
        String name = tournamentPhase.getName().toLowerCase().trim();
        for (TournamentPhaseDTO phase : existingPhases) {
            String phaseName = phase.getName().toLowerCase().trim();
            if (name.equals(phaseName) && !phase.getId().equals(tournamentPhase.getId())) {
                throw new ObjectValidationException(
                        new FieldValidationErrors()
                            .add("name", "This tournament already has a phase with that name.")
                );
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
