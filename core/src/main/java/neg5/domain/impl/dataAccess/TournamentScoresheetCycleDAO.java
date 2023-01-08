package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentScoresheetCycle;

@Singleton
public class TournamentScoresheetCycleDAO extends AbstractDAO<TournamentScoresheetCycle, Long> {

    protected TournamentScoresheetCycleDAO() {
        super(TournamentScoresheetCycle.class);
    }
}
