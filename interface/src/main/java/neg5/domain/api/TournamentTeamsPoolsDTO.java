package neg5.domain.api;

import java.util.List;

public class TournamentTeamsPoolsDTO {

    private String teamId;
    private List<TournamentTeamPoolDTO> pools;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<TournamentTeamPoolDTO> getPools() {
        return pools;
    }

    public void setPools(List<TournamentTeamPoolDTO> pools) {
        this.pools = pools;
    }
}
