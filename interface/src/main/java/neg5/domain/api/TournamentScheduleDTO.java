package neg5.domain.api;

import java.util.List;
import neg5.domain.api.enums.ScheduleStatus;

public class TournamentScheduleDTO {

    private String tournamentId;
    private String phaseId;
    private ScheduleStatus status;

    private List<TournamentScheduledMatchDTO> matches;

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
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
