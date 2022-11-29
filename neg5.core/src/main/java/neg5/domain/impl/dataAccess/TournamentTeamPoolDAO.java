package neg5.domain.impl.dataAccess;

import neg5.domain.impl.entities.TournamentTeamPool;
import neg5.domain.impl.entities.compositeIds.TournamentTeamPoolId;

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
