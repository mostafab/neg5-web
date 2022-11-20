package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.neg5.TournamentRulesDTO;
import org.neg5.mappers.TournamentRulesMapper;

public class TournamentRulesManager {

    private final TournamentManager tournamentManager;
    private final TournamentRulesMapper tournamentRulesMapper;

    @Inject
    public TournamentRulesManager(TournamentManager tournamentManager,
                                  TournamentRulesMapper tournamentRulesMapper) {
        this.tournamentManager = tournamentManager;
        this.tournamentRulesMapper = tournamentRulesMapper;
    }

    @Transactional
    public TournamentRulesDTO get(String tournamentId) {
        return tournamentRulesMapper.toDTO(tournamentManager.get(tournamentId));
    }
}
