package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentScoresheetCycleAnswer;

@Singleton
public class TournamentScoresheetCycleAnswerDAO
        extends AbstractDAO<TournamentScoresheetCycleAnswer, Long> {

    protected TournamentScoresheetCycleAnswerDAO() {
        super(TournamentScoresheetCycleAnswer.class);
    }
}
