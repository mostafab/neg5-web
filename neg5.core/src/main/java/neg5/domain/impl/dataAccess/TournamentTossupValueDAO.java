package neg5.domain.impl.dataAccess;

import neg5.domain.impl.entities.TournamentTossupValue;
import neg5.domain.impl.entities.compositeIds.TournamentTossupValueId;

public class TournamentTossupValueDAO
        extends AbstractDAO<TournamentTossupValue, TournamentTossupValueId> {

    protected TournamentTossupValueDAO() {
        super(TournamentTossupValue.class);
    }

    @Override
    protected String getTournamentIdAttributePath() {
        return "id.tournament.id";
    }
}
