package neg5.domain.impl.scheduling;

import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import neg5.domain.api.TournamentScheduledMatchDTO;

@Singleton
public class RoundRobinScheduler {

    public List<TournamentScheduledMatchDTO> generateRoundRobinMatches(Set<String> teamIds) {
        return new ArrayList<>();
    }
}
