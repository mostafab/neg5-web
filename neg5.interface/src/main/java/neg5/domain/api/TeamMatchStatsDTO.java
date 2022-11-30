package neg5.domain.api;

import java.math.BigDecimal;
import java.util.Set;
import neg5.domain.api.enums.MatchResult;

public class TeamMatchStatsDTO {

    private String opponentTeamId;
    private Integer round;

    private MatchResult result;

    private Double points;
    private Double opponentPoints;
    private Double bouncebackPoints;

    private Set<AnswersDTO> tossupAnswerCounts;
    private Double tossupsHeard;
    private BigDecimal pointsPerTossupHeard;

    private BigDecimal powersToNegRatio;
    private BigDecimal getsToNegRatio;

    private Integer bonusesHeard;
    private Double bonusPoints;

    private BigDecimal pointsPerBonus;

    public String getOpponentTeamId() {
        return opponentTeamId;
    }

    public void setOpponentTeamId(String opponentTeamId) {
        this.opponentTeamId = opponentTeamId;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Double getOpponentPoints() {
        return opponentPoints;
    }

    public void setOpponentPoints(Double opponentPoints) {
        this.opponentPoints = opponentPoints;
    }

    public Double getBouncebackPoints() {
        return bouncebackPoints;
    }

    public void setBouncebackPoints(Double bouncebackPoints) {
        this.bouncebackPoints = bouncebackPoints;
    }

    public Set<AnswersDTO> getTossupAnswerCounts() {
        return tossupAnswerCounts;
    }

    public void setTossupAnswerCounts(Set<AnswersDTO> tossupAnswerCounts) {
        this.tossupAnswerCounts = tossupAnswerCounts;
    }

    public Double getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Double tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public BigDecimal getPointsPerTossupHeard() {
        return pointsPerTossupHeard;
    }

    public void setPointsPerTossupHeard(BigDecimal pointsPerTossupHeard) {
        this.pointsPerTossupHeard = pointsPerTossupHeard;
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

    public Integer getBonusesHeard() {
        return bonusesHeard;
    }

    public void setBonusesHeard(Integer bonusesHeard) {
        this.bonusesHeard = bonusesHeard;
    }

    public Double getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(Double bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public BigDecimal getPointsPerBonus() {
        return pointsPerBonus;
    }

    public void setPointsPerBonus(BigDecimal pointsPerBonus) {
        this.pointsPerBonus = pointsPerBonus;
    }
}
