package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.MatchTeam;
import neg5.domain.impl.entities.compositeIds.MatchTeamId;

@Singleton
public class MatchTeamDAO extends AbstractDAO<MatchTeam, MatchTeamId> {

    protected MatchTeamDAO() {
        super(MatchTeam.class);
    }
}
