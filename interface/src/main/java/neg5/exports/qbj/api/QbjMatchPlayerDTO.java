package neg5.exports.qbj.api;

import java.util.List;

public class QbjMatchPlayerDTO {

    private QbjReferenceDTO player;
    private Integer tossupsHeard;
    private List<QbjPlayerAnswerCountDTO> answerCounts;

    public QbjReferenceDTO getPlayer() {
        return player;
    }

    public void setPlayer(QbjReferenceDTO player) {
        this.player = player;
    }

    public Integer getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Integer tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    public List<QbjPlayerAnswerCountDTO> getAnswerCounts() {
        return answerCounts;
    }

    public void setAnswerCounts(List<QbjPlayerAnswerCountDTO> answerCounts) {
        this.answerCounts = answerCounts;
    }
}
