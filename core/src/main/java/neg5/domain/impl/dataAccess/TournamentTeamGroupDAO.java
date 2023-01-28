package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentTeamGroup;

@Singleton
public class TournamentTeamGroupDAO extends AbstractDAO<TournamentTeamGroup, Long> {

    public TournamentTeamGroupDAO() {
        super(TournamentTeamGroup.class);
    }

    @Override
    protected String getTournamentIdAttributePath() {
        return "tournamentId";
    }
}
