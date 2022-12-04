package neg5.domain.api;

import java.time.LocalDate;
import java.util.Set;

public class TournamentDTO {

    private String id;

    private String directorId;
    private String name;
    private LocalDate tournamentDate;
    private String location;
    private String questionSet;
    private String comments;

    private Boolean usesBouncebacks;
    private Boolean allowTies;
    private Long bonusPointValue;
    private Long partsPerBonus;

    private String currentPhaseId;

    private Set<TournamentPhaseDTO> phases;
    private Set<TournamentPoolDTO> divisions;
    private Set<TournamentTossupValueDTO> tossupValues;

    private Integer maxActivePlayersPerTeam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getTournamentDate() {
        return tournamentDate;
    }

    public void setTournamentDate(LocalDate tournamentDate) {
        this.tournamentDate = tournamentDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(String questionSet) {
        this.questionSet = questionSet;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<TournamentPhaseDTO> getPhases() {
        return phases;
    }

    public void setPhases(Set<TournamentPhaseDTO> phases) {
        this.phases = phases;
    }

    public Set<TournamentPoolDTO> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<TournamentPoolDTO> divisions) {
        this.divisions = divisions;
    }

    public Boolean getUsesBouncebacks() {
        return usesBouncebacks;
    }

    public void setUsesBouncebacks(Boolean usesBouncebacks) {
        this.usesBouncebacks = usesBouncebacks;
    }

    public Boolean getAllowTies() {
        return allowTies;
    }

    public void setAllowTies(Boolean allowTies) {
        this.allowTies = allowTies;
    }

    public Long getBonusPointValue() {
        return bonusPointValue;
    }

    public void setBonusPointValue(Long bonusPointValue) {
        this.bonusPointValue = bonusPointValue;
    }

    public Long getPartsPerBonus() {
        return partsPerBonus;
    }

    public void setPartsPerBonus(Long partsPerBonus) {
        this.partsPerBonus = partsPerBonus;
    }

    public Set<TournamentTossupValueDTO> getTossupValues() {
        return tossupValues;
    }

    public void setTossupValues(Set<TournamentTossupValueDTO> tossupValues) {
        this.tossupValues = tossupValues;
    }

    public String getCurrentPhaseId() {
        return currentPhaseId;
    }

    public void setCurrentPhaseId(String currentPhaseId) {
        this.currentPhaseId = currentPhaseId;
    }

    public Integer getMaxActivePlayersPerTeam() {
        return maxActivePlayersPerTeam;
    }

    public void setMaxActivePlayersPerTeam(Integer maxActivePlayersPerTeam) {
        this.maxActivePlayersPerTeam = maxActivePlayersPerTeam;
    }
}
