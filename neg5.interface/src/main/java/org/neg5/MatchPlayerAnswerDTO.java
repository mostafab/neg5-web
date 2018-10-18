package org.neg5;

import org.neg5.enums.TossupAnswerType;

public class MatchPlayerAnswerDTO {

    private String playerId;
    private String matchId;
    private Integer tossupValue;
    private Integer numberGotten;
    private TossupAnswerType answerType;

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

    public Integer getTossupValue() {
        return tossupValue;
    }

    public void setTossupValue(Integer tossupValue) {
        this.tossupValue = tossupValue;
    }

    public Integer getNumberGotten() {
        return numberGotten;
    }

    public void setNumberGotten(Integer numberGotten) {
        this.numberGotten = numberGotten;
    }

    public TossupAnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(TossupAnswerType answerType) {
        this.answerType = answerType;
    }
}
