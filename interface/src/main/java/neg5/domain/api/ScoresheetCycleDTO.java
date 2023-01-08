package neg5.domain.api;

import java.util.List;

public class ScoresheetCycleDTO {

    private Long id;
    private Long scoresheetId;
    private Integer number;
    private String stage;

    private List<ScoresheetCycleAnswerDTO> answers;
    private List<ScoresheetCycleBonusesDTO> bonuses;
    private List<String> activePlayers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScoresheetId() {
        return scoresheetId;
    }

    public void setScoresheetId(Long scoresheetId) {
        this.scoresheetId = scoresheetId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public List<ScoresheetCycleAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ScoresheetCycleAnswerDTO> answers) {
        this.answers = answers;
    }

    public List<ScoresheetCycleBonusesDTO> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<ScoresheetCycleBonusesDTO> bonuses) {
        this.bonuses = bonuses;
    }

    public List<String> getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(List<String> activePlayers) {
        this.activePlayers = activePlayers;
    }
}
