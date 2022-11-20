package org.neg5;

public class TournamentRulesDTO {

    private Boolean usesBouncebacks;
    private Long bonusPointValue;
    private Long partsPerBonus;
    private Integer maxActivePlayersPerTeam;

    public Boolean getUsesBouncebacks() {
        return usesBouncebacks;
    }

    public void setUsesBouncebacks(Boolean usesBouncebacks) {
        this.usesBouncebacks = usesBouncebacks;
    }

    public Long getBonusPointValue() {
        return bonusPointValue;
    }

    public void setBonusPointValue(Long bonusPointValue) {
        this.bonusPointValue = bonusPointValue;
    }

    public Long getPartsPerBonus() {
        return partsPerBonus;
    }

    public void setPartsPerBonus(Long partsPerBonus) {
        this.partsPerBonus = partsPerBonus;
    }

    public Integer getMaxActivePlayersPerTeam() {
        return maxActivePlayersPerTeam;
    }

    public void setMaxActivePlayersPerTeam(Integer maxActivePlayersPerTeam) {
        this.maxActivePlayersPerTeam = maxActivePlayersPerTeam;
    }
}
