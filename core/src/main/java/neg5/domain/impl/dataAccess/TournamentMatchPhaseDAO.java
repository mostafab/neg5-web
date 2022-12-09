package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import java.util.List;
import neg5.domain.impl.entities.TournamentMatchPhase;
import neg5.domain.impl.entities.compositeIds.MatchPhaseId;

@Singleton
public class TournamentMatchPhaseDAO extends AbstractDAO<TournamentMatchPhase, MatchPhaseId> {

    TournamentMatchPhaseDAO() {
        super(TournamentMatchPhase.class);
    }

    public List<TournamentMatchPhase> findByMatch(String matchId) {
        return getEntityManager()
                .createQuery(
                        "SELECT mp from TournamentMatchPhase mp where mp.id.match.id = :matchId",
                        TournamentMatchPhase.class)
                .setParameter("matchId", matchId)
                .getResultList();
    }
}
