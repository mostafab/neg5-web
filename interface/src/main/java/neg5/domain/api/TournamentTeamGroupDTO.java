package neg5.domain.api;

import java.util.List;
import neg5.domain.api.enums.State;

public class TournamentTeamGroupDTO {

    private Long id;
    private String tournamentId;
    private String name;
    private State stateCode;

    private List<TournamentTeamDTO> rosters;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getStateCode() {
        return stateCode;
    }

    public void setStateCode(State stateCode) {
        this.stateCode = stateCode;
    }

    public List<TournamentTeamDTO> getRosters() {
        return rosters;
    }

    public void setRosters(List<TournamentTeamDTO> rosters) {
        this.rosters = rosters;
    }
}
