package org.neg5.managers.stats;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.TournamentMatchApi;
import org.neg5.RoundStatDTO;
import org.neg5.RoundsReportStatsDTO;
import org.neg5.TournamentMatchDTO;
import org.neg5.managers.stats.aggregators.RoundStatsAggregator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class RoundReportStatsManager {

    @Inject private TournamentMatchApi tournamentMatchManager;

    @Inject private StatsCacheManager statsCacheManager;

    public RoundsReportStatsDTO getCachedStats(String tournamentId, String phaseId) {
        return statsCacheManager.getCache(RoundsReportStatsDTO.class).getOrAdd(tournamentId, phaseId, () -> calculateRoundReportStats(tournamentId, phaseId))
                .orElseGet(() -> calculateRoundReportStats(tournamentId, phaseId));
    }

    public RoundsReportStatsDTO calculateRoundReportStats(String tournamentId, String phaseId) {
        Map<Long, List<TournamentMatchDTO>> matchesByRound = groupMatchesByRound(tournamentId, phaseId);

        RoundsReportStatsDTO stats = new RoundsReportStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);
        stats.setRounds(
                matchesByRound.entrySet().stream()
                    .map(entry -> calculateRoundStat(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList())
        );

        return stats;
    }

    private Map<Long, List<TournamentMatchDTO>> groupMatchesByRound(String tournamentId,
                                                                    String phaseId) {
        List<TournamentMatchDTO> matches = tournamentMatchManager.findAllByTournamentAndPhase(tournamentId, phaseId);
        return matches.stream()
                .filter(match -> match.getRound() != null)
                .collect(Collectors.groupingBy(TournamentMatchDTO::getRound));
    }

    private RoundStatDTO calculateRoundStat(Long round, List<TournamentMatchDTO> matches) {
        RoundStatsAggregator aggregator = new RoundStatsAggregator(round.intValue());
        matches.forEach(aggregator::accept);
        return aggregator.collect();
    }
}
