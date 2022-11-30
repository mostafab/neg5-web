package neg5.domain.impl.dataAccess;

import java.util.List;
import neg5.domain.impl.entities.Tournament;

public class TournamentDAO extends AbstractDAO<Tournament, String> {

    public TournamentDAO() {
        super(Tournament.class);
    }

    public List<Tournament> getTournamentsOwnedByUser(String userId) {
        return getEntityManager()
                .createQuery(
                        "SELECT t from Tournament t where t.director.id = :userId",
                        Tournament.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
