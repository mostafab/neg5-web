package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentPool;

@Singleton
public class TournamentPoolDAO extends AbstractDAO<TournamentPool, String> {

    protected TournamentPoolDAO() {
        super(TournamentPool.class);
    }
}
