package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.neg5.TournamentPhaseDTO;
import org.neg5.TournamentPoolDTO;
import org.neg5.daos.TournamentPoolDAO;
import org.neg5.data.TournamentPool;
import org.neg5.mappers.TournamentPoolMapper;

import java.util.Objects;

public class TournamentPoolManager
        extends AbstractDTOManager<TournamentPool, TournamentPoolDTO, String> {

    private final TournamentPoolMapper mapper;
    private final TournamentPoolDAO divisionDAO;

    private final TournamentPhaseManager phaseManager;

    @Inject
    public TournamentPoolManager(TournamentPoolMapper mapper,
                                 TournamentPoolDAO divisionDAO,
                                 TournamentPhaseManager phaseManager) {
        this.mapper = mapper;
        this.divisionDAO = divisionDAO;
        this.phaseManager = phaseManager;
    }

    @Override
    @Transactional
    public TournamentPoolDTO create(TournamentPoolDTO tournamentPoolDTO) {
        validatePoolPhaseAndTournamentIdAlign(tournamentPoolDTO);
        return super.create(tournamentPoolDTO);
    }

    @Override
    @Transactional
    public TournamentPoolDTO update(TournamentPoolDTO tournamentPoolDTO) {
        TournamentPoolDTO original = get(tournamentPoolDTO.getId());
        tournamentPoolDTO.setTournamentId(original.getTournamentId());
        tournamentPoolDTO.setPhaseId(original.getPhaseId());
        return super.update(tournamentPoolDTO);
    }

    @Override
    protected TournamentPoolDAO getRwDAO() {
        return divisionDAO;
    }

    @Override
    protected TournamentPoolMapper getMapper() {
        return mapper;
    }

    private void validatePoolPhaseAndTournamentIdAlign(TournamentPoolDTO tournamentPoolDTO) {
        String tournamentId = tournamentPoolDTO.getTournamentId();
        TournamentPhaseDTO phase = phaseManager.get(tournamentPoolDTO.getPhaseId());

        if (!Objects.equals(tournamentId, phase.getTournamentId())) {
            throw new IllegalArgumentException("Division's tournament id does not match phase's tournament id");
        }
    }
}
