package org.neg5.daos;

import neg5.domain.impl.entities.TournamentTeamPool;
import org.neg5.data.embeddables.TournamentTeamPoolId;

import java.util.List;

public class TournamentTeamPoolDAO
        extends AbstractDAO<TournamentTeamPool, TournamentTeamPoolId> {

    protected TournamentTeamPoolDAO() {
        super(TournamentTeamPool.class);
    }

    public List<TournamentTeamPool> findByTeamId(String teamId) {
        return getEntityManager().createQuery(
                "SELECT ttm from TournamentTeamPool ttm where ttm.id.team.id = :teamId", TournamentTeamPool.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }
}
