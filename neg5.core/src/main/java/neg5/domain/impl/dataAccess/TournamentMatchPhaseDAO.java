package neg5.domain.impl.dataAccess;

import neg5.domain.impl.entities.TournamentMatchPhase;
import neg5.domain.impl.entities.compositeIds.MatchPhaseId;

import java.util.List;

public class TournamentMatchPhaseDAO extends AbstractDAO<TournamentMatchPhase, MatchPhaseId> {

    TournamentMatchPhaseDAO() {
        super(TournamentMatchPhase.class);
    }

    public List<TournamentMatchPhase> findByMatch(String matchId) {
        return getEntityManager().createQuery(
                "SELECT mp from TournamentMatchPhase mp where mp.id.match.id = :matchId", TournamentMatchPhase.class)
                .setParameter("matchId", matchId)
                .getResultList();
    }
}
