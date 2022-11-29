package neg5.domain.api;

import neg5.domain.api.enums.TossupAnswerType;

public class TournamentTossupValueDTO {

    private String tournamentId;
    private Integer value;

    private TossupAnswerType answerType;

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TossupAnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(TossupAnswerType answerType) {
        this.answerType = answerType;
    }
}
