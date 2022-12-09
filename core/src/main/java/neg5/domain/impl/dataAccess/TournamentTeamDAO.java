package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentTeam;

@Singleton
public class TournamentTeamDAO extends AbstractDAO<TournamentTeam, String> {

    protected TournamentTeamDAO() {
        super(TournamentTeam.class);
    }
}
