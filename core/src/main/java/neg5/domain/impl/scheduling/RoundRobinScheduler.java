package neg5.domain.impl.scheduling;

import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import neg5.domain.api.TournamentScheduledMatchDTO;

@Singleton
public class RoundRobinScheduler {

    private static final String BYE = "Bye";

    public List<TournamentScheduledMatchDTO> generateRoundRobinMatches(
            Set<String> teamIds, Integer firstRound) {
        List<String> entries = new ArrayList<>(teamIds);
        if (entries.size() % 2 == 1) {
            entries.add(BYE);
        }
        int startRound = firstRound == null ? 1 : firstRound;
        Collections.shuffle(entries);
        List<TournamentScheduledMatchDTO> matches = new ArrayList<>();
        for (int round = 0; round < entries.size() - 1; round++) {
            for (int j = 0; j < entries.size() / 2; j++) {
                String team1Id = entries.get(j);
                String team2Id = entries.get(entries.size() - j - 1);

                TournamentScheduledMatchDTO match = new TournamentScheduledMatchDTO();
                match.setTeam1Id(BYE.equals(team1Id) ? null : team1Id);
                match.setTeam2Id(BYE.equals(team2Id) ? null : team2Id);
                match.setRound(round + startRound);
                matches.add(match);
            }
            Collections.rotate(entries.subList(1, entries.size()), 1);
        }
        return matches;
    }
}
