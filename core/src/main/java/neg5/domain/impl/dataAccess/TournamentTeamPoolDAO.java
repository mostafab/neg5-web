package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import java.util.List;
import neg5.domain.impl.entities.TournamentTeamPool;
import neg5.domain.impl.entities.compositeIds.TournamentTeamPoolId;

@Singleton
public class TournamentTeamPoolDAO extends AbstractDAO<TournamentTeamPool, TournamentTeamPoolId> {

    protected TournamentTeamPoolDAO() {
        super(TournamentTeamPool.class);
    }

    public List<TournamentTeamPool> findByTeamId(String teamId) {
        return getEntityManager()
                .createQuery(
                        "SELECT ttm from TournamentTeamPool ttm where ttm.id.team.id = :teamId",
                        TournamentTeamPool.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }
}
