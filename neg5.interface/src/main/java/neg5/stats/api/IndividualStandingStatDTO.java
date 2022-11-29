package neg5.stats.api;

import neg5.domain.api.AnswersDTO;

import java.math.BigDecimal;
import java.util.Set;

public class IndividualStandingStatDTO {

    private String playerId;

    private BigDecimal gamesPlayed;
    private Set<AnswersDTO> tossupAnswerCounts;
    private int tossupsHeard;
    private double totalPoints;

    private BigDecimal pointsPerTossupHeard;
    private BigDecimal pointsPerGame;

    private BigDecimal powersToNegRatio;
    private BigDecimal getsToNegRatio;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public BigDecimal getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(BigDecimal gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Set<AnswersDTO> getTossupAnswerCounts() {
        return tossupAnswerCounts;
    }

    public void setTossupAnswerCounts(Set<AnswersDTO> tossupAnswerCounts) {
        this.tossupAnswerCounts = tossupAnswerCounts;
    }

    public int getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(int tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public BigDecimal getPointsPerTossupHeard() {
        return pointsPerTossupHeard;
    }

    public void setPointsPerTossupHeard(BigDecimal pointsPerTossupHeard) {
        this.pointsPerTossupHeard = pointsPerTossupHeard;
    }

    public BigDecimal getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(BigDecimal pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public BigDecimal getPowersToNegRatio() {
        return powersToNegRatio;
    }

    public void setPowersToNegRatio(BigDecimal powersToNegRatio) {
        this.powersToNegRatio = powersToNegRatio;
    }

    public BigDecimal getGetsToNegRatio() {
        return getsToNegRatio;
    }

    public void setGetsToNegRatio(BigDecimal getsToNegRatio) {
        this.getsToNegRatio = getsToNegRatio;
    }
}
