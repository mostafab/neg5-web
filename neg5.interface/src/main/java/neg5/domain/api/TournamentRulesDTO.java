package neg5.domain.api;

import java.util.List;

public class TournamentRulesDTO {

    private Boolean usesBouncebacks;
    private Long bonusPointValue;
    private Long partsPerBonus;
    private Integer maxActivePlayersPerTeam;
    private Boolean allowTies;
    private List<TournamentTossupValueDTO> tossupValues;

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

    public Boolean getAllowTies() {
        return allowTies;
    }

    public void setAllowTies(Boolean allowTies) {
        this.allowTies = allowTies;
    }

    public List<TournamentTossupValueDTO> getTossupValues() {
        return tossupValues;
    }

    public void setTossupValues(List<TournamentTossupValueDTO> tossupValues) {
        this.tossupValues = tossupValues;
    }
}
