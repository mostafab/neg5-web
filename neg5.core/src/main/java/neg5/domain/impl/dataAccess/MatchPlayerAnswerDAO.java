package neg5.domain.impl.dataAccess;

import neg5.domain.impl.entities.MatchPlayerAnswer;
import neg5.domain.impl.entities.compositeIds.MatchPlayerAnswerId;

public class MatchPlayerAnswerDAO extends AbstractDAO<MatchPlayerAnswer, MatchPlayerAnswerId> {

    protected MatchPlayerAnswerDAO() {
        super(MatchPlayerAnswer.class);
    }
}
