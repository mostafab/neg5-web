package org.neg5.managers.stats;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.neg5.FullTeamsMatchesStatsDTO;
import org.neg5.TeamMatchesStatsDTO;
import org.neg5.TeamStandingStatsDTO;
import org.neg5.TeamStandingsStatsDTO;
import org.neg5.TournamentMatchDTO;
import org.neg5.managers.TournamentTeamMatchHelper;
import org.neg5.managers.stats.aggregators.TeamMatchesStatsAggregator;
import org.neg5.managers.stats.aggregators.TeamStandingStatAggregator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class TeamStandingsStatsManager {

    private final TournamentTeamMatchHelper tournamentTeamManager;
    private final StatsCacheManager statsCacheManager;

    @Inject
    public TeamStandingsStatsManager(TournamentTeamMatchHelper tournamentTeamManager,
                                     StatsCacheManager statsCacheManager) {
        this.tournamentTeamManager = tournamentTeamManager;
        this.statsCacheManager = statsCacheManager;
    }

    public TeamStandingsStatsDTO getCachedTeamStandings(String tournamentId, String phaseId) {
        return statsCacheManager.getCache(TeamStandingsStatsDTO.class).getOrAdd(tournamentId, phaseId, () -> calculateTeamStandings(tournamentId, phaseId))
                .orElseGet(() -> this.calculateTeamStandings(tournamentId, phaseId));
    }

    public TeamStandingsStatsDTO calculateTeamStandings(String tournamentId, String phaseId) {
        TeamStandingsStatsDTO stats = new TeamStandingsStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);

        Map<String, List<TournamentMatchDTO>> teamsByMatches = tournamentTeamManager
                .groupMatchesByTeams(tournamentId, phaseId);
        stats.setTeamStandings(
            teamsByMatches.entrySet().stream()
                .map(entry -> computeStandingsForTeam(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList())
        );

        return stats;
    }

    public FullTeamsMatchesStatsDTO getCachedFullTeamStandings(String tournamentId, String phaseId) {
        return statsCacheManager.getCache(FullTeamsMatchesStatsDTO.class).getOrAdd(tournamentId, phaseId, () -> calculateFullTeamStandings(tournamentId, phaseId))
                .orElseGet(() -> this.calculateFullTeamStandings(tournamentId, phaseId));
    }

    public FullTeamsMatchesStatsDTO calculateFullTeamStandings(String tournamentId, String phaseId) {
        FullTeamsMatchesStatsDTO stats = new FullTeamsMatchesStatsDTO();
        stats.setTournamentId(tournamentId);
        stats.setPhaseId(phaseId);

        Map<String, List<TournamentMatchDTO>> teamsByMatches = tournamentTeamManager
                .groupMatchesByTeams(tournamentId, phaseId);
        stats.setTeams(
                teamsByMatches.entrySet().stream()
                    .map(entry -> getFullStandingsForTeam(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList())
        );

        return stats;
    }

    private TeamStandingStatsDTO computeStandingsForTeam(String teamId, List<TournamentMatchDTO> matches) {
        TeamStandingStatAggregator aggregator = new TeamStandingStatAggregator(teamId);
        matches.forEach(aggregator::accept);
        return aggregator.collect();
    }

    private TeamMatchesStatsDTO getFullStandingsForTeam(String teamId,
                                                        List<TournamentMatchDTO> matches) {

        TeamMatchesStatsDTO teamMatchesStats = new TeamMatchesStatsDTO();
        teamMatchesStats.setTeamId(teamId);

        TeamMatchesStatsAggregator aggregator = new TeamMatchesStatsAggregator(teamId);
        matches.forEach(aggregator::accept);
        teamMatchesStats.setMatches(aggregator.collect());

        return teamMatchesStats;
    }
}
