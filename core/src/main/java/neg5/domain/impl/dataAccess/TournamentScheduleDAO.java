package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentSchedule;

@Singleton
public class TournamentScheduleDAO extends AbstractDAO<TournamentSchedule, Long> {

    protected TournamentScheduleDAO() {
        super(TournamentSchedule.class);
    }
}
