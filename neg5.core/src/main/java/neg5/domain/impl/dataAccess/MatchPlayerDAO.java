package neg5.domain.impl.dataAccess;

import neg5.domain.impl.entities.MatchPlayer;
import neg5.domain.impl.entities.embeddables.MatchPlayerId;

public class MatchPlayerDAO extends AbstractDAO<MatchPlayer, MatchPlayerId> {

    protected MatchPlayerDAO() {
        super(MatchPlayer.class);
    }
}
