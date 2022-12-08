package neg5.domain.api;

import java.util.List;
import neg5.stats.api.BaseAggregateStatsDTO;

public class RoundsReportStatsDTO extends BaseAggregateStatsDTO {

    private List<RoundStatDTO> rounds;

    public List<RoundStatDTO> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundStatDTO> rounds) {
        this.rounds = rounds;
    }
}
