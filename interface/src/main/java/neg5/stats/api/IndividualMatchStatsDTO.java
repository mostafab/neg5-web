package neg5.stats.api;

import java.math.BigDecimal;
import java.util.Set;
import neg5.domain.api.AnswersDTO;

public class IndividualMatchStatsDTO {

    private String opponentTeamId;
    private Integer round;

    private Double points;

    private Set<AnswersDTO> tossupAnswerCounts;

    private Double tossupsHeard;
    private BigDecimal percentGamePlayed;

    private BigDecimal pointsPerTossup;

    private BigDecimal powersToNegRatio;
    private BigDecimal getsToNegRatio;

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

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
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

    public BigDecimal getPercentGamePlayed() {
        return percentGamePlayed;
    }

    public void setPercentGamePlayed(BigDecimal percentGamePlayed) {
        this.percentGamePlayed = percentGamePlayed;
    }

    public BigDecimal getPointsPerTossup() {
        return pointsPerTossup;
    }

    public void setPointsPerTossup(BigDecimal pointsPerTossup) {
        this.pointsPerTossup = pointsPerTossup;
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
