package neg5.domain.api;

import neg5.domain.api.enums.ScheduledMatchStatus;

public class TournamentScheduledMatchDTO {

    private Integer round;
    private String team1Id;
    private String team2Id;
    private ScheduledMatchStatus status;
    private String room;

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
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

    public ScheduledMatchStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduledMatchStatus status) {
        this.status = status;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
