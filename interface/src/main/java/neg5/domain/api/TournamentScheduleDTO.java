package neg5.domain.api;

import java.util.List;

public class TournamentScheduleDTO {

    private String tournamentPhaseId;

    private List<TournamentScheduledMatchDTO> matches;

    public String getTournamentPhaseId() {
        return tournamentPhaseId;
    }

    public void setTournamentPhaseId(String tournamentPhaseId) {
        this.tournamentPhaseId = tournamentPhaseId;
    }

    public List<TournamentScheduledMatchDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<TournamentScheduledMatchDTO> matches) {
        this.matches = matches;
    }
}
