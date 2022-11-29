package neg5.domain.impl.dataAccess;

import neg5.domain.impl.entities.MatchTeam;
import neg5.domain.impl.entities.embeddables.MatchTeamId;

public class MatchTeamDAO extends AbstractDAO<MatchTeam, MatchTeamId> {

    protected MatchTeamDAO() {
        super(MatchTeam.class);
    }
}
