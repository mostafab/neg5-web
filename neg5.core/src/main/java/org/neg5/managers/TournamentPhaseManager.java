package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.neg5.FieldValidationErrors;
import org.neg5.TournamentPhaseDTO;

import org.neg5.daos.TournamentPhaseDAO;
import org.neg5.data.TournamentPhase;
import org.neg5.mappers.TournamentPhaseMapper;
import org.neg5.validation.ObjectValidationException;

import java.util.List;

public class TournamentPhaseManager extends AbstractDTOManager<TournamentPhase, TournamentPhaseDTO, String> {

    private final TournamentPhaseDAO rwTournamentPhaseDAO;
    private final TournamentPhaseMapper tournamentPhaseMapper;

    @Inject
    public TournamentPhaseManager(TournamentPhaseDAO rwTournamentPhaseDAO,
                                  TournamentPhaseMapper tournamentPhaseMapper) {
        this.rwTournamentPhaseDAO = rwTournamentPhaseDAO;
        this.tournamentPhaseMapper = tournamentPhaseMapper;
    }

    @Override
    @Transactional
    public TournamentPhaseDTO create(TournamentPhaseDTO tournamentPhaseDTO) {
        ensureUniquePhaseName(tournamentPhaseDTO);
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

    private void ensureUniquePhaseName(TournamentPhaseDTO tournamentPhase) {
        List<TournamentPhaseDTO> existingPhases = findAllByTournamentId(tournamentPhase.getTournamentId());
        String name = tournamentPhase.getName().toLowerCase().trim();
        for (TournamentPhaseDTO phase : existingPhases) {
            String phaseName = phase.getName().toLowerCase().trim();
            if (name.equals(phaseName)) {
                throw new ObjectValidationException(
                        new FieldValidationErrors()
                            .add("name", "Phase name must be unique within the tournament")
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
