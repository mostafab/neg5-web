package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentScoresheet;

@Singleton
public class TournamentScoresheetDAO extends AbstractDAO<TournamentScoresheet, Long> {

    protected TournamentScoresheetDAO() {
        super(TournamentScoresheet.class);
    }
}
