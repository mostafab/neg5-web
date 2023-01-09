package neg5.domain.api;

public class TournamentScoresheetCycleBonusesDTO {

    private Long id;
    private Long cycleId;
    private Integer number;
    private String answeringTeamId;
    private Integer value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

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
