package neg5.exports.qbj.api;

import java.util.List;

public class QbjPoolDTO {

    private String name;
    private List<QbjPoolTeamDTO> poolTeams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QbjPoolTeamDTO> getPoolTeams() {
        return poolTeams;
    }

    public void setPoolTeams(List<QbjPoolTeamDTO> poolTeams) {
        this.poolTeams = poolTeams;
    }
}
