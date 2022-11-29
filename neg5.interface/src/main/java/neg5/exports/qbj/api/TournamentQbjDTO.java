package neg5.exports.qbj.api;

import java.util.List;

public class TournamentQbjDTO {

    private String name;
    private String questionSet;
    private String startDate;

    private TournamentSiteDTO tournamentSite;
    private ScoringRulesDTO scoringRules;

    private List<RegistrationDTO> registrations;

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

    public TournamentSiteDTO getTournamentSite() {
        return tournamentSite;
    }

    public void setTournamentSite(TournamentSiteDTO tournamentSite) {
        this.tournamentSite = tournamentSite;
    }

    public ScoringRulesDTO getScoringRules() {
        return scoringRules;
    }

    public void setScoringRules(ScoringRulesDTO scoringRules) {
        this.scoringRules = scoringRules;
    }

    public List<RegistrationDTO> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<RegistrationDTO> registrations) {
        this.registrations = registrations;
    }
}
