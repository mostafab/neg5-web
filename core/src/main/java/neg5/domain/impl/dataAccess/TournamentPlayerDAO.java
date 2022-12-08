package neg5.domain.impl.dataAccess;

import java.util.List;
import neg5.domain.impl.entities.TournamentPlayer;

public class TournamentPlayerDAO extends AbstractDAO<TournamentPlayer, String> {

    protected TournamentPlayerDAO() {
        super(TournamentPlayer.class);
    }

    public List<TournamentPlayer> findByTeamId(String teamId) {
        return getEntityManager()
                .createQuery(
                        "SELECT tp from TournamentPlayer tp WHERE tp.team.id = :teamId",
                        TournamentPlayer.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }
}
