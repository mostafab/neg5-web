package neg5.stats.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import neg5.domain.api.RoundStatDTO;
import neg5.domain.api.RoundsReportStatsDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.enums.StatReportType;
import neg5.stats.impl.aggregators.RoundStatsAggregator;
import neg5.stats.impl.cache.TournamentStatsCache;

@Singleton
class RoundReportStatsManager {

    @Inject private TournamentMatchApi tournamentMatchManager;
    @Inject private TournamentStatsCache cache;

    RoundsReportStatsDTO getCachedStats(String tournamentId, String phaseId) {
        return cache.getOrAdd(
                StatReportType.ROUND_REPORT,
                tournamentId,
                phaseId,
                () -> calculateRoundReportStats(tournamentId, phaseId));
    }

    RoundsReportStatsDTO calculateRoundReportStats(String tournamentId, String phaseId) {
        Map<Long, List<TournamentMatchDTO>> matchesByRound =
                groupMatchesByRound(tournamentId, phaseId);

        RoundsReportStatsDTO stats = new RoundsReportStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);
        stats.setRounds(
                matchesByRound.entrySet().stream()
                        .map(entry -> calculateRoundStat(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList()));

        return stats;
    }

    private Map<Long, List<TournamentMatchDTO>> groupMatchesByRound(
            String tournamentId, String phaseId) {
        List<TournamentMatchDTO> matches =
                tournamentMatchManager.findAllByTournamentAndPhase(tournamentId, phaseId);
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
