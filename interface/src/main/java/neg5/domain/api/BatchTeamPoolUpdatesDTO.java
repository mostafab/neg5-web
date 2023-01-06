package neg5.domain.api;

import java.util.List;
import java.util.Set;

public class BatchTeamPoolUpdatesDTO {

    private List<TeamPoolAssignmentsDTO> assignments;
    private Set<String> poolsToRemove;

    public List<TeamPoolAssignmentsDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<TeamPoolAssignmentsDTO> assignments) {
        this.assignments = assignments;
    }

    public Set<String> getPoolsToRemove() {
        return poolsToRemove;
    }

    public void setPoolsToRemove(Set<String> poolsToRemove) {
        this.poolsToRemove = poolsToRemove;
    }
}
