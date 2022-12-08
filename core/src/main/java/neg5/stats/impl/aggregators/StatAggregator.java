package neg5.stats.impl.aggregators;

import neg5.domain.api.TournamentMatchDTO;

/**
 * Interface used to stream matches into an aggregator and calculate a total stats result
 *
 * @param <StatType>
 */
public interface StatAggregator<StatType> {

    /**
     * Take in a match and update metrics
     *
     * @param match the match
     */
    void accept(TournamentMatchDTO match);

    /**
     * Aggregate all current input matches into some custom return object
     *
     * @return the aggregation of stats
     */
    StatType collect();
}
