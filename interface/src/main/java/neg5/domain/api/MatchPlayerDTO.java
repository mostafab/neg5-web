package neg5.domain.api;

import java.util.Set;

public class MatchPlayerDTO {

    private String playerId;
    private String matchId;
    private String tournamentId;

    private Integer tossupsHeard;

    private Set<MatchPlayerAnswerDTO> answers;

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

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Integer tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public Set<MatchPlayerAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<MatchPlayerAnswerDTO> answers) {
        this.answers = answers;
    }
}
