package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentScheduledMatch;

@Singleton
public class TournamentScheduleMatchDAO extends AbstractDAO<TournamentScheduledMatch, Long> {

    protected TournamentScheduleMatchDAO() {
        super(TournamentScheduledMatch.class);
    }
}
