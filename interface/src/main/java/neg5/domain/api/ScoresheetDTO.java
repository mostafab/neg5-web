package neg5.domain.api;

import java.util.List;
import java.util.Set;

public class ScoresheetDTO {

    private String team1Id;
    private String team2Id;

    private List<ScoresheetCycleDTO> cycles;
    private ScoresheetCycleDTO currentCycle;

    private Set<String> phases;
    private Integer round;
    private String room;
    private String moderator;
    private String packet;
    private String notes;

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

    public ScoresheetCycleDTO getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(ScoresheetCycleDTO currentCycle) {
        this.currentCycle = currentCycle;
    }

    public Set<String> getPhases() {
        return phases;
    }

    public void setPhases(Set<String> phases) {
        this.phases = phases;
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
}
