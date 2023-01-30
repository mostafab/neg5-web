package neg5.exports.qbj.api;

public class AnswerTypeDTO {

    private String id;
    private final QbjObjectType type = QbjObjectType.ANSWER_TYPE;
    private Integer value;
    private Boolean awardsBonus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Boolean getAwardsBonus() {
        return awardsBonus;
    }

    public void setAwardsBonus(Boolean awardsBonus) {
        this.awardsBonus = awardsBonus;
    }

    public QbjObjectType getType() {
        return type;
    }
}
