package neg5.domain.api;

import java.util.Set;

public class TeamPoolAssignmentsDTO {

    private Set<PoolAssignmentDTO> assignments;
    private String teamId;

    public Set<PoolAssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<PoolAssignmentDTO> assignments) {
        this.assignments = assignments;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
