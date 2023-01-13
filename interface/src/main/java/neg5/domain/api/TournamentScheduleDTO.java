package neg5.domain.api;

import java.util.List;
import neg5.domain.api.enums.ScheduleStatus;

public class TournamentScheduleDTO {

    private String tournamentPhaseId;
    private ScheduleStatus status;

    private List<TournamentScheduledMatchDTO> matches;

    public String getTournamentPhaseId() {
        return tournamentPhaseId;
    }

    public void setTournamentPhaseId(String tournamentPhaseId) {
        this.tournamentPhaseId = tournamentPhaseId;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public List<TournamentScheduledMatchDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<TournamentScheduledMatchDTO> matches) {
        this.matches = matches;
    }
}
