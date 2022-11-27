package org.neg5.daos;

import org.neg5.data.TournamentPlayer;

import java.util.List;

public class TournamentPlayerDAO extends AbstractDAO<TournamentPlayer, String> {

    protected TournamentPlayerDAO() {
        super(TournamentPlayer.class);
    }

    public List<TournamentPlayer> findByTeamId(String teamId) {
        return getEntityManager().createQuery("SELECT tp from TournamentPlayer tp WHERE tp.team.id = :teamId", TournamentPlayer.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }
}
