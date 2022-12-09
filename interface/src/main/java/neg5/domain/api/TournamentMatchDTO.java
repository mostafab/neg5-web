package neg5.domain.api;

import java.time.Instant;
import java.util.Set;

public class TournamentMatchDTO {

    private String id;
    private String tournamentId;

    private Long round;
    private String room;
    private String moderator;

    private String packet;
    private Integer tossupsHeard;
    private Boolean isTiebreaker;

    private String notes;
    private String serialId;

    private Set<MatchTeamDTO> teams;

    private Set<String> phases;

    private Instant addedAt;

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

    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
        this.round = round;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public Integer getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Integer tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public Boolean getIsTiebreaker() {
        return isTiebreaker;
    }

    public void setIsTiebreaker(Boolean isTiebreaker) {
        this.isTiebreaker = isTiebreaker;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Set<MatchTeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(Set<MatchTeamDTO> teams) {
        this.teams = teams;
    }

    public Set<String> getPhases() {
        return phases;
    }

    public void setPhases(Set<String> phases) {
        this.phases = phases;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }
}
