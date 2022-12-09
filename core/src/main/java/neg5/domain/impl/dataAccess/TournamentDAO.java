package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import java.time.LocalDate;
import java.util.List;
import neg5.domain.impl.entities.Tournament;

@Singleton
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

    public List<Tournament> findByMatchingPrefix(String name) {
        String queryParam = name.toLowerCase().concat("%");
        return getEntityManager()
                .createQuery(
                        "SELECT t from Tournament t WHERE lower(t.name) like :name",
                        Tournament.class)
                .setParameter("name", queryParam)
                .getResultList();
    }

    public List<Tournament> findTournamentsBetweenDates(LocalDate start, LocalDate end) {
        return getEntityManager()
                .createQuery(
                        "SELECT t from Tournament t WHERE t.tournamentDate >= :start AND t.tournamentDate <= :end",
                        Tournament.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
}
