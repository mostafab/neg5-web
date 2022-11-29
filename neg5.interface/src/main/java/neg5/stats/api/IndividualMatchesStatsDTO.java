package neg5.stats.api;

import java.util.List;

public class IndividualMatchesStatsDTO {

    private String playerId;
    private List<IndividualMatchStatsDTO> matches;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public List<IndividualMatchStatsDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<IndividualMatchStatsDTO> matches) {
        this.matches = matches;
    }
}
