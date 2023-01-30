package neg5.exports.qbj.api;

import java.util.List;

public class QbjTournamentDTO {

    private final QbjObjectType type = QbjObjectType.TOURNAMENT;

    private String name;
    private String questionSet;
    private String startDate;

    private QbjTournamentSiteDTO tournamentSite;
    private QbjScoringRulesDTO scoringRules;
    private List<QbjReferenceDTO> registrations;
    private List<QbjReferenceDTO> phases;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(String questionSet) {
        this.questionSet = questionSet;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public QbjTournamentSiteDTO getTournamentSite() {
        return tournamentSite;
    }

    public void setTournamentSite(QbjTournamentSiteDTO tournamentSite) {
        this.tournamentSite = tournamentSite;
    }

    public QbjScoringRulesDTO getScoringRules() {
        return scoringRules;
    }

    public void setScoringRules(QbjScoringRulesDTO scoringRules) {
        this.scoringRules = scoringRules;
    }

    public List<QbjReferenceDTO> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<QbjReferenceDTO> registrations) {
        this.registrations = registrations;
    }

    public List<QbjReferenceDTO> getPhases() {
        return phases;
    }

    public void setPhases(List<QbjReferenceDTO> phases) {
        this.phases = phases;
    }

    public QbjObjectType getType() {
        return type;
    }
}
