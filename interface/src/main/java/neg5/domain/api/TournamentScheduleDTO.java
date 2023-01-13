package neg5.domain.api;

import java.util.List;

public class TournamentScheduleDTO {

    private Long id;
    private String tournamentId;
    private String tournamentPhaseId;

    private List<TournamentScheduledMatchDTO> matches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

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
