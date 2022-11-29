package neg5.stats.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.stats.api.TournamentStatsApi;
import org.neg5.FullIndividualMatchesStatsDTO;
import org.neg5.FullTeamsMatchesStatsDTO;
import org.neg5.IndividualStandingsStatsDTO;
import org.neg5.RoundsReportStatsDTO;
import org.neg5.TeamStandingsStatsDTO;
import org.neg5.managers.stats.IndividualStandingsStatsManager;
import org.neg5.managers.stats.RoundReportStatsManager;
import org.neg5.managers.stats.TeamStandingsStatsManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Singleton
public class TournamentStatsApiImpl implements TournamentStatsApi {

    private final IndividualStandingsStatsManager individualStandingsStatsManager;
    private final TeamStandingsStatsManager teamStandingsStatsManager;
    private final RoundReportStatsManager roundReportStatsManager;

    @Inject
    public TournamentStatsApiImpl(IndividualStandingsStatsManager individualStandingsStatsManager,
                                  TeamStandingsStatsManager teamStandingsStatsManager,
                                  RoundReportStatsManager roundReportStatsManager) {
        this.individualStandingsStatsManager = individualStandingsStatsManager;
        this.teamStandingsStatsManager = teamStandingsStatsManager;
        this.roundReportStatsManager = roundReportStatsManager;
    }

    @Override
    public FullIndividualMatchesStatsDTO calculateFullIndividualStats(@Nonnull String tournamentId, @Nullable String phaseId) {
        return individualStandingsStatsManager.getCachedFullIndividualStats(tournamentId, phaseId);
    }

    @Override
    public IndividualStandingsStatsDTO calculateIndividualStandings(@Nonnull String tournamentId, @Nullable String phaseId) {
        return individualStandingsStatsManager.getCachedIndividualStandings(tournamentId, phaseId);
    }

    @Override
    public TeamStandingsStatsDTO calculateTeamStandings(@Nonnull String tournamentId, @Nullable String phaseId) {
        return teamStandingsStatsManager.getCachedTeamStandings(tournamentId, phaseId);
    }

    @Override
    public FullTeamsMatchesStatsDTO calculateFullTeamStandings(@Nonnull String tournamentId, @Nullable String phaseId) {
        return teamStandingsStatsManager.getCachedFullTeamStandings(tournamentId, phaseId);
    }

    @Override
    public RoundsReportStatsDTO calculateRoundReportStats(@Nonnull String tournamentId, @Nullable String phaseId) {
        return roundReportStatsManager.getCachedStats(tournamentId, phaseId);
    }
}
