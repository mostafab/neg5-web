package neg5.stats.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerApi;
import neg5.stats.api.FullIndividualMatchesStatsDTO;
import neg5.stats.api.IndividualMatchesStatsDTO;
import neg5.stats.api.IndividualStandingStatDTO;
import neg5.stats.api.IndividualStandingsStatsDTO;
import neg5.stats.impl.aggregators.IndividualMatchesStatAggregator;
import neg5.stats.impl.aggregators.IndividualStandingStatAggregator;

@Singleton
class IndividualStandingsStatsManager {

    @Inject private TournamentPlayerApi tournamentPlayerApi;

    @Inject private StatsCacheManager statsCacheManager;

    public IndividualStandingsStatsDTO getCachedIndividualStandings(
            String tournamentId, String phaseId) {
        return statsCacheManager
                .getCache(IndividualStandingsStatsDTO.class)
                .getOrAdd(
                        tournamentId,
                        phaseId,
                        () -> calculateIndividualStandings(tournamentId, phaseId))
                .orElseGet(() -> calculateIndividualStandings(tournamentId, phaseId));
    }

    public IndividualStandingsStatsDTO calculateIndividualStandings(
            String tournamentId, String phaseId) {
        IndividualStandingsStatsDTO stats = new IndividualStandingsStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);

        Map<String, List<TournamentMatchDTO>> matchesByPlayers =
                tournamentPlayerApi.groupMatchesByPlayers(tournamentId, phaseId);

        stats.setPlayerStandings(
                matchesByPlayers.entrySet().stream()
                        .map(entry -> computeStandingsForPlayer(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList()));

        return stats;
    }

    public FullIndividualMatchesStatsDTO getCachedFullIndividualStats(
            String tournamentId, String phaseId) {
        return statsCacheManager
                .getCache(FullIndividualMatchesStatsDTO.class)
                .getOrAdd(
                        tournamentId, phaseId, () -> getFullIndividualStats(tournamentId, phaseId))
                .orElseGet(() -> getFullIndividualStats(tournamentId, phaseId));
    }

    public FullIndividualMatchesStatsDTO getFullIndividualStats(
            String tournamentId, String phaseId) {
        FullIndividualMatchesStatsDTO stats = new FullIndividualMatchesStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);

        Map<String, List<TournamentMatchDTO>> matchesByPlayers =
                tournamentPlayerApi.groupMatchesByPlayers(tournamentId, phaseId);

        stats.setPlayers(
                matchesByPlayers.entrySet().stream()
                        .map(
                                entry ->
                                        computeSinglePlayerFullResults(
                                                entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList()));
        return stats;
    }

    private IndividualStandingStatDTO computeStandingsForPlayer(
            String playerId, List<TournamentMatchDTO> matches) {
        IndividualStandingStatAggregator aggregator =
                new IndividualStandingStatAggregator(playerId);
        matches.forEach(aggregator::accept);
        return aggregator.collect();
    }

    private IndividualMatchesStatsDTO computeSinglePlayerFullResults(
            String playerId, List<TournamentMatchDTO> matches) {
        IndividualMatchesStatsDTO stats = new IndividualMatchesStatsDTO();
        stats.setPlayerId(playerId);

        IndividualMatchesStatAggregator aggregator = new IndividualMatchesStatAggregator(playerId);
        matches.forEach(aggregator::accept);

        stats.setMatches(aggregator.collect());

        return stats;
    }
}
