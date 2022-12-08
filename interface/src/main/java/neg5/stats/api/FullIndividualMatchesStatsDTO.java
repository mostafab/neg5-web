package neg5.stats.api;

import java.util.List;

public class FullIndividualMatchesStatsDTO extends BaseAggregateStatsDTO {

    private List<IndividualMatchesStatsDTO> players;

    public List<IndividualMatchesStatsDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<IndividualMatchesStatsDTO> players) {
        this.players = players;
    }
}
