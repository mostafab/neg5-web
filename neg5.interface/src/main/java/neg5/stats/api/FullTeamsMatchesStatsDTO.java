package neg5.stats.api;

import neg5.domain.api.TeamMatchesStatsDTO;

import java.util.List;

public class FullTeamsMatchesStatsDTO extends BaseAggregateStatsDTO {

    private List<TeamMatchesStatsDTO> teams;

    public List<TeamMatchesStatsDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamMatchesStatsDTO> teams) {
        this.teams = teams;
    }
}
