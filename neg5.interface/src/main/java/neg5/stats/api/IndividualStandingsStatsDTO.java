package neg5.stats.api;

import java.util.List;

public class IndividualStandingsStatsDTO extends BaseAggregateStatsDTO {

    private List<IndividualStandingStatDTO> playerStandings;

    public List<IndividualStandingStatDTO> getPlayerStandings() {
        return playerStandings;
    }

    public void setPlayerStandings(List<IndividualStandingStatDTO> playerStandings) {
        this.playerStandings = playerStandings;
    }
}
