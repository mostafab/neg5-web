package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.stats.api.TournamentStatsApi;

public class TournamentStatsRoutes extends AbstractJsonRoutes {

    @Inject private TournamentStatsApi tournamentStatsApi;

    @Override
    protected String getBasePath() {
        return "/neg5-api/tournaments/:id/stats";
    }

    @Override
    public void registerRoutes() {
        get(
                "/team-standings",
                (request, response) ->
                        tournamentStatsApi.calculateTeamStandings(
                                request.params("id"), request.queryParams("phase")));
        get(
                "/individual-standings",
                (request, response) ->
                        tournamentStatsApi.calculateIndividualStandings(
                                request.params("id"), request.queryParams("phase")));
        get(
                "/team-full-standings",
                (request, response) ->
                        tournamentStatsApi.calculateFullTeamStandings(
                                request.params("id"), request.queryParams("phase")));
        get(
                "/individual-full-standings",
                (request, response) ->
                        tournamentStatsApi.calculateFullIndividualStats(
                                request.params("id"), request.queryParams("phase")));
        get(
                "/round-report",
                (request, response) ->
                        tournamentStatsApi.calculateRoundReportStats(
                                request.params("id"), request.queryParams("phase")));

        post(
                "/invalidate",
                (request, response) -> tournamentStatsApi.invalidateStats(request.params("id")));
    }
}
