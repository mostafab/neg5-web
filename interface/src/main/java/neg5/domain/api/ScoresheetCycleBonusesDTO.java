package neg5.domain.api;

public class ScoresheetCycleBonusesDTO {

    private String answeringTeamId;
    private Integer value;

    public String getAnsweringTeamId() {
        return answeringTeamId;
    }

    public void setAnsweringTeamId(String answeringTeamId) {
        this.answeringTeamId = answeringTeamId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
