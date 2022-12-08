package neg5.domain.api;

import java.math.BigDecimal;

public class TeamRecordDTO {

    private int wins;
    private int losses;
    private int ties;
    private BigDecimal winPercentage;

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getTies() {
        return ties;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }

    public BigDecimal getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(BigDecimal winPercentage) {
        this.winPercentage = winPercentage;
    }
}
