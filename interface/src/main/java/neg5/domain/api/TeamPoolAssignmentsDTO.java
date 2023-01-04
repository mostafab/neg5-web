package neg5.domain.api;

import java.util.Set;

public class TeamPoolAssignmentsDTO {

    private Set<String> poolIds;
    private String teamId;

    public Set<String> getPoolIds() {
        return poolIds;
    }

    public void setPoolIds(Set<String> poolIds) {
        this.poolIds = poolIds;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
