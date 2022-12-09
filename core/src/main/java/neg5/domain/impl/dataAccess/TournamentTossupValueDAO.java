package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentTossupValue;
import neg5.domain.impl.entities.compositeIds.TournamentTossupValueId;

@Singleton
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
