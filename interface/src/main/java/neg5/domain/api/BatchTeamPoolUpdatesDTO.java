package neg5.domain.api;

import java.util.List;

public class BatchTeamPoolUpdatesDTO {

    private List<TeamPoolAssignmentsDTO> assignments;

    public List<TeamPoolAssignmentsDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<TeamPoolAssignmentsDTO> assignments) {
        this.assignments = assignments;
    }
}
