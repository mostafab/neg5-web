package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.MatchPlayer;
import neg5.domain.impl.entities.compositeIds.MatchPlayerId;

@Singleton
public class MatchPlayerDAO extends AbstractDAO<MatchPlayer, MatchPlayerId> {

    protected MatchPlayerDAO() {
        super(MatchPlayer.class);
    }
}
