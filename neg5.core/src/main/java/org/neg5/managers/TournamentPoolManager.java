package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.neg5.TournamentPhaseDTO;
import org.neg5.TournamentPoolDTO;
import org.neg5.daos.TournamentPoolDAO;
import org.neg5.data.TournamentPool;
import org.neg5.mappers.TournamentPoolMapper;

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
        TournamentPhaseDTO phase = phaseManager.get(tournamentPoolDTO.getPhaseId());
        tournamentPoolDTO.setTournamentId(phase.getTournamentId());
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
    protected TournamentPoolDAO getDao() {
        return divisionDAO;
    }

    @Override
    protected TournamentPoolMapper getMapper() {
        return mapper;
    }
}
