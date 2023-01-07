package neg5.domain.api;

import java.util.List;

public class ScoresheetCycleDTO {

    private Integer number;
    private String stage;

    private List<ScoresheetCycleAnswersDTO> answers;
    private List<ScoresheetCycleBonusesDTO> bonuses;
    private List<String> activePlayers;

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

    public List<ScoresheetCycleAnswersDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ScoresheetCycleAnswersDTO> answers) {
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
