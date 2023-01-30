package neg5.exports.qbj.api;

public class QbjTournamentDTO {

    private final QbjObjectType type = QbjObjectType.TOURNAMENT;

    private String name;
    private String questionSet;
    private String startDate;

    private QbjTournamentSiteDTO tournamentSite;
    private QbjScoringRulesDTO scoringRules;

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

    public QbjObjectType getType() {
        return type;
    }
}
