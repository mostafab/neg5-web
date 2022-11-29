package neg5.domain.impl.entities.transformers.data;

import java.util.Set;

public class TeamMatchPlayer {

    private String playerId;
    private String matchId;
    private String teamId;
    private Integer tossupsHeard;

    private Set<TeamMatchPlayerAnswer> tossupValues;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Integer tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public Set<TeamMatchPlayerAnswer> getTossupValues() {
        return tossupValues;
    }

    public void setTossupValues(Set<TeamMatchPlayerAnswer> tossupValues) {
        this.tossupValues = tossupValues;
    }
}
