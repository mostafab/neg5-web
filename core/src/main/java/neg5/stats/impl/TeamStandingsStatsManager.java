package neg5.stats.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import neg5.domain.api.TeamMatchesStatsDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.enums.StatReportType;
import neg5.stats.api.FullTeamsMatchesStatsDTO;
import neg5.stats.api.TeamStandingStatsDTO;
import neg5.stats.api.TeamStandingsStatsDTO;
import neg5.stats.impl.aggregators.TeamMatchesStatsAggregator;
import neg5.stats.impl.aggregators.TeamStandingStatAggregator;
import neg5.stats.impl.cache.TournamentStatsCache;

@Singleton
class TeamStandingsStatsManager {

    private final TournamentMatchApi tournamentMatchApi;
    private final TournamentStatsCache cache;

    @Inject
    public TeamStandingsStatsManager(
            TournamentMatchApi tournamentMatchApi, TournamentStatsCache cache) {
        this.tournamentMatchApi = tournamentMatchApi;
        this.cache = cache;
    }

    public TeamStandingsStatsDTO getCachedTeamStandings(String tournamentId, String phaseId) {
        return cache.getOrAdd(
                StatReportType.TEAM_STANDINGS,
                tournamentId,
                phaseId,
                () -> calculateTeamStandings(tournamentId, phaseId));
    }

    public TeamStandingsStatsDTO calculateTeamStandings(String tournamentId, String phaseId) {
        TeamStandingsStatsDTO stats = new TeamStandingsStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);

        Map<String, List<TournamentMatchDTO>> teamsByMatches =
                tournamentMatchApi.groupMatchesByTeams(tournamentId, phaseId);
        stats.setTeamStandings(
                teamsByMatches.entrySet().stream()
                        .map(entry -> computeStandingsForTeam(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList()));

        return stats;
    }

    public FullTeamsMatchesStatsDTO getCachedFullTeamStandings(
            String tournamentId, String phaseId) {
        return cache.getOrAdd(
                StatReportType.TEAM_FULL,
                tournamentId,
                phaseId,
                () -> calculateFullTeamStandings(tournamentId, phaseId));
    }

    public FullTeamsMatchesStatsDTO calculateFullTeamStandings(
            String tournamentId, String phaseId) {
        FullTeamsMatchesStatsDTO stats = new FullTeamsMatchesStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);

        Map<String, List<TournamentMatchDTO>> teamsByMatches =
                tournamentMatchApi.groupMatchesByTeams(tournamentId, phaseId);
        stats.setTeams(
                teamsByMatches.entrySet().stream()
                        .map(entry -> getFullStandingsForTeam(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList()));

        return stats;
    }

    private TeamStandingStatsDTO computeStandingsForTeam(
            String teamId, List<TournamentMatchDTO> matches) {
        TeamStandingStatAggregator aggregator = new TeamStandingStatAggregator(teamId);
        matches.forEach(aggregator::accept);
        return aggregator.collect();
    }

    private TeamMatchesStatsDTO getFullStandingsForTeam(
            String teamId, List<TournamentMatchDTO> matches) {

        TeamMatchesStatsDTO teamMatchesStats = new TeamMatchesStatsDTO();
        teamMatchesStats.setTeamId(teamId);

        TeamMatchesStatsAggregator aggregator = new TeamMatchesStatsAggregator(teamId);
        matches.forEach(aggregator::accept);
        teamMatchesStats.setMatches(aggregator.collect());

        return teamMatchesStats;
    }
}
