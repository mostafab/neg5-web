package neg5.exports.qbj.api;

import java.util.List;

public class QbjMatchTeamDTO {

    private QbjReferenceDTO team;
    private Boolean forfeitLoss;
    private Integer bonusPoints;
    private Integer correctTossupsWithoutBonuses;
    private Integer bonusBouncebackPoints;

    private List<QbjMatchPlayerDTO> matchPlayers;

    public QbjReferenceDTO getTeam() {
        return team;
    }

    public void setTeam(QbjReferenceDTO team) {
        this.team = team;
    }

    public Boolean getForfeitLoss() {
        return forfeitLoss;
    }

    public void setForfeitLoss(Boolean forfeitLoss) {
        this.forfeitLoss = forfeitLoss;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(Integer bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public Integer getCorrectTossupsWithoutBonuses() {
        return correctTossupsWithoutBonuses;
    }

    public void setCorrectTossupsWithoutBonuses(Integer correctTossupsWithoutBonuses) {
        this.correctTossupsWithoutBonuses = correctTossupsWithoutBonuses;
    }

    public Integer getBonusBouncebackPoints() {
        return bonusBouncebackPoints;
    }

    public void setBonusBouncebackPoints(Integer bonusBouncebackPoints) {
        this.bonusBouncebackPoints = bonusBouncebackPoints;
    }

    public List<QbjMatchPlayerDTO> getMatchPlayers() {
        return matchPlayers;
    }

    public void setMatchPlayers(List<QbjMatchPlayerDTO> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }
}
