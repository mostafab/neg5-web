package neg5.domain.api;

import java.math.BigDecimal;
import java.util.Set;

public class RoundStatDTO {

    private Integer round;
    private BigDecimal averagePointsPerGame;

    private Double tossupPoints;
    private Double tossupsHeard;
    private Integer numMatches;

    private BigDecimal tossupPointsPerTossupHeard;
    private BigDecimal averagePointsPerBonus;
    private Set<AnswersDTO> tossupAnswerCounts;

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public BigDecimal getAveragePointsPerGame() {
        return averagePointsPerGame;
    }

    public void setAveragePointsPerGame(BigDecimal averagePointsPerGame) {
        this.averagePointsPerGame = averagePointsPerGame;
    }

    public Double getTossupPoints() {
        return tossupPoints;
    }

    public void setTossupPoints(Double tossupPoints) {
        this.tossupPoints = tossupPoints;
    }

    public Double getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Double tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public Integer getNumMatches() {
        return numMatches;
    }

    public void setNumMatches(Integer numMatches) {
        this.numMatches = numMatches;
    }

    public BigDecimal getTossupPointsPerTossupHeard() {
        return tossupPointsPerTossupHeard;
    }

    public void setTossupPointsPerTossupHeard(BigDecimal tossupPointsPerTossupHeard) {
        this.tossupPointsPerTossupHeard = tossupPointsPerTossupHeard;
    }

    public BigDecimal getAveragePointsPerBonus() {
        return averagePointsPerBonus;
    }

    public void setAveragePointsPerBonus(BigDecimal averagePointsPerBonus) {
        this.averagePointsPerBonus = averagePointsPerBonus;
    }

    public Set<AnswersDTO> getTossupAnswerCounts() {
        return tossupAnswerCounts;
    }

    public void setTossupAnswerCounts(Set<AnswersDTO> tossupAnswerCounts) {
        this.tossupAnswerCounts = tossupAnswerCounts;
    }
}
