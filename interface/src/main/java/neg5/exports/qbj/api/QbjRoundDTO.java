package neg5.exports.qbj.api;

import java.util.List;

public class QbjRoundDTO {

    private String name;
    private List<QbjReferenceDTO> matches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QbjReferenceDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<QbjReferenceDTO> matches) {
        this.matches = matches;
    }
}
