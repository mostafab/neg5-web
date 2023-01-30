package neg5.exports.qbj.api;

import java.util.List;

public class QbjPhaseDTO {

    private String id;
    private final QbjObjectType type = QbjObjectType.PHASE;
    private String name;
    private List<QbjRoundDTO> rounds;

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

    public QbjObjectType getType() {
        return type;
    }

    public List<QbjRoundDTO> getRounds() {
        return rounds;
    }

    public void setRounds(List<QbjRoundDTO> rounds) {
        this.rounds = rounds;
    }
}
