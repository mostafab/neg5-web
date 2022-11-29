package org.neg5.daos;

import neg5.domain.impl.entities.TournamentTeam;

public class TournamentTeamDAO extends AbstractDAO<TournamentTeam, String> {

    protected TournamentTeamDAO() {
        super(TournamentTeam.class);
    }
}
