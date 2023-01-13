package neg5.domain.api;

public class ScheduleGenerationRequestDTO {

    private String tournamentPhaseId;
    private Integer firstRound;

    public String getTournamentPhaseId() {
        return tournamentPhaseId;
    }

    public void setTournamentPhaseId(String tournamentPhaseId) {
        this.tournamentPhaseId = tournamentPhaseId;
    }

    public Integer getFirstRound() {
        return firstRound;
    }

    public void setFirstRound(Integer firstRound) {
        this.firstRound = firstRound;
    }
}
