package neg5.domain.impl.entities.transformers.data;

import java.util.Set;

public class TeamInMatch {

    private String matchId;
    private String teamId;

    private Integer score;
    private Integer bouncebackPoints;

    private Integer overtimeTossups;
    private Boolean forfeit;

    private Set<TeamMatchPlayer> players;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getBouncebackPoints() {
        return bouncebackPoints;
    }

    public void setBouncebackPoints(Integer bouncebackPoints) {
        this.bouncebackPoints = bouncebackPoints;
    }

    public Integer getOvertimeTossups() {
        return overtimeTossups;
    }

    public void setOvertimeTossups(Integer overtimeTossups) {
        this.overtimeTossups = overtimeTossups;
    }

    public Boolean getForfeit() {
        return forfeit;
    }

    public void setForfeit(Boolean forfeit) {
        this.forfeit = forfeit;
    }

    public Set<TeamMatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(Set<TeamMatchPlayer> players) {
        this.players = players;
    }
}
