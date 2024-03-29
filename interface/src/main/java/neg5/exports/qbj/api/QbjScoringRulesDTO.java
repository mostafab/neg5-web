package neg5.exports.qbj.api;

import java.util.List;

public class QbjScoringRulesDTO {

    private Integer maximumPlayersPerTeam;

    private Long regulationTossupCount;

    private Long pointsPerBonusPart;
    private Long maximumBonusScore;
    private Long minimumPartsPerBonus;
    private Boolean bonusesBounceBack;

    private List<AnswerTypeDTO> answerTypes;

    public Integer getMaximumPlayersPerTeam() {
        return maximumPlayersPerTeam;
    }

    public void setMaximumPlayersPerTeam(Integer maximumPlayersPerTeam) {
        this.maximumPlayersPerTeam = maximumPlayersPerTeam;
    }

    public Long getRegulationTossupCount() {
        return regulationTossupCount;
    }

    public void setRegulationTossupCount(Long regulationTossupCount) {
        this.regulationTossupCount = regulationTossupCount;
    }

    public Long getPointsPerBonusPart() {
        return pointsPerBonusPart;
    }

    public void setPointsPerBonusPart(Long pointsPerBonusPart) {
        this.pointsPerBonusPart = pointsPerBonusPart;
    }

    public Long getMaximumBonusScore() {
        return maximumBonusScore;
    }

    public void setMaximumBonusScore(Long maximumBonusScore) {
        this.maximumBonusScore = maximumBonusScore;
    }

    public Long getMinimumPartsPerBonus() {
        return minimumPartsPerBonus;
    }

    public void setMinimumPartsPerBonus(Long minimumPartsPerBonus) {
        this.minimumPartsPerBonus = minimumPartsPerBonus;
    }

    public Boolean getBonusesBounceBack() {
        return bonusesBounceBack;
    }

    public void setBonusesBounceBack(Boolean bonusesBounceBack) {
        this.bonusesBounceBack = bonusesBounceBack;
    }

    public List<AnswerTypeDTO> getAnswerTypes() {
        return answerTypes;
    }

    public void setAnswerTypes(List<AnswerTypeDTO> answerTypes) {
        this.answerTypes = answerTypes;
    }
}
