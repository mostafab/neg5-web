package neg5.domain.api;

import java.util.List;
import java.util.Set;
import neg5.domain.api.enums.ScoresheetStatus;

public class ScoresheetDTO {

    private Long id;
    private ScoresheetStatus status;
    private String tournamentId;
    private String team1Id;
    private String team2Id;

    private List<ScoresheetCycleDTO> cycles;

    private Set<String> phases;
    private Set<String> activePlayers;
    private Integer round;
    private String room;
    private String moderator;
    private String packet;
    private String notes;
    private Boolean isTiebreaker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScoresheetStatus getStatus() {
        return status;
    }

    public void setStatus(ScoresheetStatus status) {
        this.status = status;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public List<ScoresheetCycleDTO> getCycles() {
        return cycles;
    }

    public void setCycles(List<ScoresheetCycleDTO> cycles) {
        this.cycles = cycles;
    }

    public Set<String> getPhases() {
        return phases;
    }

    public void setPhases(Set<String> phases) {
        this.phases = phases;
    }

    public Set<String> getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(Set<String> activePlayers) {
        this.activePlayers = activePlayers;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getIsTiebreaker() {
        return isTiebreaker;
    }

    public void setIsTiebreaker(Boolean isTiebreaker) {
        this.isTiebreaker = isTiebreaker;
    }
}
