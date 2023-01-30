package neg5.exports.qbj.api;

import java.util.List;

public class QbjTeamDTO {

    private String id;
    private String name;
    private List<QbjPlayerDTO> players;

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

    public List<QbjPlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<QbjPlayerDTO> players) {
        this.players = players;
    }
}
