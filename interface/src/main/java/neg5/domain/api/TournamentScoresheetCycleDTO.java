package neg5.domain.api;

import java.util.List;

public class TournamentScoresheetCycleDTO {

    private Long id;
    private Long scoresheetId;
    private Integer number;
    private String stage;

    private List<TournamentScoresheetCycleAnswerDTO> answers;
    private List<TournamentScoresheetCycleBonusesDTO> bonuses;
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

    public List<TournamentScoresheetCycleAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<TournamentScoresheetCycleAnswerDTO> answers) {
        this.answers = answers;
    }

    public List<TournamentScoresheetCycleBonusesDTO> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<TournamentScoresheetCycleBonusesDTO> bonuses) {
        this.bonuses = bonuses;
    }

    public List<String> getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(List<String> activePlayers) {
        this.activePlayers = activePlayers;
    }
}
