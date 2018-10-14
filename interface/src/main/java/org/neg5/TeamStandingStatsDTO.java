package org.neg5;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class TeamStandingStatsDTO {

    private String teamId;

    private BigDecimal pointsPerGame;
    private BigDecimal pointsAgainstPerGame;
    private BigDecimal marginOfVictory;
    private BigDecimal pointsPerTossupHeard;

    private int tossupsHeard;
    private TeamRecordDTO record;

    private Set<AnswersDTO> tossupAnswerCounts;

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
}
