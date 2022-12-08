package neg5.domain.api;

import neg5.domain.api.enums.TossupAnswerType;

public class AnswersDTO {

    private Integer value;
    private Integer total;
    private TossupAnswerType answerType;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public TossupAnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(TossupAnswerType answerType) {
        this.answerType = answerType;
    }
}
