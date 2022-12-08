package neg5.stats.api;

import java.util.List;
import neg5.domain.api.TeamMatchesStatsDTO;

public class FullTeamsMatchesStatsDTO extends BaseAggregateStatsDTO {

    private List<TeamMatchesStatsDTO> teams;

    public List<TeamMatchesStatsDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamMatchesStatsDTO> teams) {
        this.teams = teams;
    }
}
