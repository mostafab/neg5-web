package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import java.util.List;
import neg5.domain.impl.entities.TournamentScheduledMatch;

@Singleton
public class TournamentScheduleMatchDAO extends AbstractDAO<TournamentScheduledMatch, Long> {

    protected TournamentScheduleMatchDAO() {
        super(TournamentScheduledMatch.class);
    }

    public List<Long> getIdsForSchedule(Long scheduleId) {
        return getEntityManager()
                .createQuery(
                        "SELECT m.id FROM TournamentScheduledMatch m WHERE m.schedule.id = :scheduleId",
                        Long.class)
                .setParameter("scheduleId", scheduleId)
                .getResultList();
    }
}
