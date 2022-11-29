package org.neg5.daos;

import neg5.domain.impl.entities.TournamentPhase;

public class TournamentPhaseDAO extends AbstractDAO<TournamentPhase, String> {

    protected TournamentPhaseDAO() {
        super(TournamentPhase.class);
    }
}
