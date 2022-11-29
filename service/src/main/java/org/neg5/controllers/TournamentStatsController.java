package org.neg5.controllers;

import com.google.inject.Inject;
import neg5.stats.api.TournamentStatsApi;
import neg5.stats.impl.StatsCacheManager;

public class TournamentStatsController extends AbstractJsonController {

    @Inject private TournamentStatsApi tournamentStatsApi;

    @Inject private StatsCacheManager statsCacheManager;

    @Override
    protected String getBasePath() {
        return "/neg5-api/tournaments/:id/stats";
    }

    @Override
    public void registerRoutes() {
        get("/team-standings", (request, response) ->
                tournamentStatsApi.calculateTeamStandings(request.params("id"), request.queryParams("phase")));
        get("/individual-standings", (request, response) ->
                tournamentStatsApi.calculateIndividualStandings(request.params("id"), request.queryParams("phase")));
        get("/team-full-standings", (request, response) ->
                tournamentStatsApi.calculateFullTeamStandings(request.params("id"), request.queryParams("phase")));
        get("/individual-full-standings", (request, response) ->
                tournamentStatsApi.calculateFullIndividualStats(request.params("id"), request.queryParams("phase")));
        get("/round-report", (request, response) ->
                tournamentStatsApi.calculateRoundReportStats(request.params("id"), request.queryParams("phase")));

        post("/invalidate",
                (request, response) -> statsCacheManager.invalidateStats(request.params("id")));
    }
}
