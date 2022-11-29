package neg5.stats.api;

import org.neg5.FullIndividualMatchesStatsDTO;
import org.neg5.FullTeamsMatchesStatsDTO;
import org.neg5.IndividualStandingsStatsDTO;
import org.neg5.RoundsReportStatsDTO;
import org.neg5.TeamStandingsStatsDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface TournamentStatsApi {

    IndividualStandingsStatsDTO calculateIndividualStandings(@Nonnull String tournamentId,
                                                             @Nullable String phaseId);

    FullIndividualMatchesStatsDTO calculateFullIndividualStats(@Nonnull String tournamentId,
                                                               @Nullable String phaseId);

    TeamStandingsStatsDTO calculateTeamStandings(@Nonnull String tournamentId, @Nullable String phaseId);

    FullTeamsMatchesStatsDTO calculateFullTeamStandings(@Nonnull String tournamentId, @Nullable String phaseId);

    RoundsReportStatsDTO calculateRoundReportStats(@Nonnull String tournamentId, @Nullable String phaseId);
}
