package org.neg5.managers.stats.aggregators;

import org.neg5.TournamentMatchDTO;

/**
 * Interface used to stream matches into an aggregator and calculate a total stats result
 * @param <StatType>
 */
interface StatAggregator<StatType> {

    /**
     * Take in a match and update metrics
     * @param match the match
     */
    void accept(TournamentMatchDTO match);

    /**
     * Aggregate all current input matches into some custom return object
     * @return the aggregation of stats
     */
    StatType collect();
}
