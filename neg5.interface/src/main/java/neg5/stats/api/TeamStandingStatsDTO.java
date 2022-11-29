package neg5.stats.api;

import neg5.domain.api.AnswersDTO;
import neg5.domain.api.TeamRecordDTO;

import java.math.BigDecimal;
import java.util.Set;

public class TeamStandingStatsDTO {

    private String teamId;

    private BigDecimal pointsPerGame;
    private BigDecimal pointsAgainstPerGame;
    private BigDecimal marginOfVictory;
    private BigDecimal pointsPerTossupHeard;
    private BigDecimal pointsPerBonus;

    private int tossupsHeard;
    private TeamRecordDTO record;

    private Set<AnswersDTO> tossupAnswerCounts;

    private BigDecimal powersToNegRatio;
    private BigDecimal getsToNegRatio;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public BigDecimal getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(BigDecimal pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public BigDecimal getPointsAgainstPerGame() {
        return pointsAgainstPerGame;
    }

    public void setPointsAgainstPerGame(BigDecimal pointsAgainstPerGame) {
        this.pointsAgainstPerGame = pointsAgainstPerGame;
    }

    public Set<AnswersDTO> getTossupAnswerCounts() {
        return tossupAnswerCounts;
    }

    public void setTossupAnswerCounts(Set<AnswersDTO> tossupAnswerCounts) {
        this.tossupAnswerCounts = tossupAnswerCounts;
    }

    public BigDecimal getMarginOfVictory() {
        return marginOfVictory;
    }

    public void setMarginOfVictory(BigDecimal marginOfVictory) {
        this.marginOfVictory = marginOfVictory;
    }

    public int getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(int tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public TeamRecordDTO getRecord() {
        return record;
    }

    public void setRecord(TeamRecordDTO record) {
        this.record = record;
    }

    public BigDecimal getPointsPerTossupHeard() {
        return pointsPerTossupHeard;
    }

    public void setPointsPerTossupHeard(BigDecimal pointsPerTossupHeard) {
        this.pointsPerTossupHeard = pointsPerTossupHeard;
    }

    public BigDecimal getPointsPerBonus() {
        return pointsPerBonus;
    }

    public void setPointsPerBonus(BigDecimal pointsPerBonus) {
        this.pointsPerBonus = pointsPerBonus;
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
