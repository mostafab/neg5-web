package neg5.domain.impl.entities.transformers.data;

import java.sql.Timestamp;
import java.util.Set;

public class Match {

    private String id;
    private String tournamentId;
    private Integer round;
    private Integer tossupsHeard;
    private Set<Phase> phases;
    private Set<TeamInMatch> teams;
    private String moderator;
    private String notes;
    private String packet;
    private String serialId;
    private Timestamp addedAt;
    private Boolean isTiebreaker;
    private Long scoresheetId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Integer tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public Set<Phase> getPhases() {
        return phases;
    }

    public void setPhases(Set<Phase> phases) {
        this.phases = phases;
    }

    public Set<TeamInMatch> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamInMatch> teams) {
        this.teams = teams;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    public Boolean getIsTiebreaker() {
        return isTiebreaker;
    }

    public void setIsTiebreaker(Boolean isTiebreaker) {
        this.isTiebreaker = isTiebreaker;
    }

    public Long getScoresheetId() {
        return scoresheetId;
    }

    public void setScoresheetId(Long scoresheetId) {
        this.scoresheetId = scoresheetId;
    }
}
