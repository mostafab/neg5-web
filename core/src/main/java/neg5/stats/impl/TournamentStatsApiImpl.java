package neg5.stats.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import neg5.domain.api.RoundsReportStatsDTO;
import neg5.stats.api.FullIndividualMatchesStatsDTO;
import neg5.stats.api.FullTeamsMatchesStatsDTO;
import neg5.stats.api.IndividualStandingsStatsDTO;
import neg5.stats.api.StatsCacheInvalidationResultDTO;
import neg5.stats.api.TeamStandingsStatsDTO;
import neg5.stats.api.TournamentStatsApi;

@Singleton
public class TournamentStatsApiImpl implements TournamentStatsApi {

    private final IndividualStandingsStatsManager individualStandingsStatsManager;
    private final TeamStandingsStatsManager teamStandingsStatsManager;
    private final RoundReportStatsManager roundReportStatsManager;
    private final StatsCacheBroker cacheBroker;

    @Inject
    public TournamentStatsApiImpl(
            IndividualStandingsStatsManager individualStandingsStatsManager,
            TeamStandingsStatsManager teamStandingsStatsManager,
            RoundReportStatsManager roundReportStatsManager,
            StatsCacheBroker cacheBroker) {
        this.individualStandingsStatsManager = individualStandingsStatsManager;
        this.teamStandingsStatsManager = teamStandingsStatsManager;
        this.roundReportStatsManager = roundReportStatsManager;
        this.cacheBroker = cacheBroker;
    }

    @Override
    public FullIndividualMatchesStatsDTO calculateFullIndividualStats(
            @Nonnull String tournamentId, @Nullable String phaseId) {
        return individualStandingsStatsManager.getCachedFullIndividualStats(tournamentId, phaseId);
    }

    @Override
    public IndividualStandingsStatsDTO calculateIndividualStandings(
            @Nonnull String tournamentId, @Nullable String phaseId) {
        return individualStandingsStatsManager.getCachedIndividualStandings(tournamentId, phaseId);
    }

    @Override
    public TeamStandingsStatsDTO calculateTeamStandings(
            @Nonnull String tournamentId, @Nullable String phaseId) {
        return teamStandingsStatsManager.getCachedTeamStandings(tournamentId, phaseId);
    }

    @Override
    public FullTeamsMatchesStatsDTO calculateFullTeamStandings(
            @Nonnull String tournamentId, @Nullable String phaseId) {
        return teamStandingsStatsManager.getCachedFullTeamStandings(tournamentId, phaseId);
    }

    @Override
    public RoundsReportStatsDTO calculateRoundReportStats(
            @Nonnull String tournamentId, @Nullable String phaseId) {
        return roundReportStatsManager.getCachedStats(tournamentId, phaseId);
    }

    @Override
    public StatsCacheInvalidationResultDTO invalidateStats(String tournamentId) {
        return cacheBroker.invalidateStats(tournamentId);
    }
}
