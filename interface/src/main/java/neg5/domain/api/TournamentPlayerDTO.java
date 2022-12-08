package neg5.domain.api;

import neg5.domain.api.enums.PlayerYear;

public class TournamentPlayerDTO {

    private String id;
    private String name;
    private String teamId;
    private String tournamentId;
    private PlayerYear year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public PlayerYear getYear() {
        return year;
    }

    public void setYear(PlayerYear year) {
        this.year = year;
    }
}
