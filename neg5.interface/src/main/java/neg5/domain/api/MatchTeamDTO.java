package neg5.domain.api;

import java.util.List;

public class MatchTeamDTO {

    private String matchId;
    private String teamId;
    private String tournamentId;

    private Integer score;
    private Integer bouncebackPoints;
    private Integer overtimeTossupsGotten;

    private List<MatchPlayerDTO> players;

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

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
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

    public Integer getOvertimeTossupsGotten() {
        return overtimeTossupsGotten;
    }

    public void setOvertimeTossupsGotten(Integer overtimeTossupsGotten) {
        this.overtimeTossupsGotten = overtimeTossupsGotten;
    }

    public List<MatchPlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<MatchPlayerDTO> players) {
        this.players = players;
    }
}
