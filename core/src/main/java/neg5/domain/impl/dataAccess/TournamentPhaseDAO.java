package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentPhase;

@Singleton
public class TournamentPhaseDAO extends AbstractDAO<TournamentPhase, String> {

    protected TournamentPhaseDAO() {
        super(TournamentPhase.class);
    }
}
