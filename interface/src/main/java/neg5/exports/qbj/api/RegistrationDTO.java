package neg5.exports.qbj.api;

import java.util.List;

public class RegistrationDTO {

    private String name;
    private List<QbjTeamDTO> teams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QbjTeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<QbjTeamDTO> teams) {
        this.teams = teams;
    }
}
