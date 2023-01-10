package neg5.domain.api;

public class ScheduleGenerationRequestDTO {

    private String tournamentId;
    private String phaseId;
    private Integer firstRound;

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

    public Integer getFirstRound() {
        return firstRound;
    }

    public void setFirstRound(Integer firstRound) {
        this.firstRound = firstRound;
    }
}
