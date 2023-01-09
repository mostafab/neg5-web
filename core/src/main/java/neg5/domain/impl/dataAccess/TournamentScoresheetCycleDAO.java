package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import java.util.List;
import neg5.domain.impl.entities.TournamentScoresheetCycle;

@Singleton
public class TournamentScoresheetCycleDAO extends AbstractDAO<TournamentScoresheetCycle, Long> {

    protected TournamentScoresheetCycleDAO() {
        super(TournamentScoresheetCycle.class);
    }

    public List<Long> getCycleIdsForScoresheet(Long scoresheetId) {
        return getEntityManager()
                .createQuery(
                        "SELECT c.id FROM TournamentScoresheetCycle c WHERE c.scoresheet.id = :scoresheetId",
                        Long.class)
                .setParameter("scoresheetId", scoresheetId)
                .getResultList();
    }
}
