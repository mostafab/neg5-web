package neg5.domain.api;

import java.util.List;

public class TeamMatchesStatsDTO {

    private String teamId;
    private List<TeamMatchStatsDTO> matches;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<TeamMatchStatsDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<TeamMatchStatsDTO> matches) {
        this.matches = matches;
    }
}
