package neg5.exports.qbj.api;

import java.util.List;

public class QbjRegistrationDTO {

    private String id;
    private String name;
    private final QbjObjectType type = QbjObjectType.REGISTRATION;
    private List<QbjTeamDTO> teams;

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

    public List<QbjTeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<QbjTeamDTO> teams) {
        this.teams = teams;
    }

    public QbjObjectType getType() {
        return type;
    }
}
