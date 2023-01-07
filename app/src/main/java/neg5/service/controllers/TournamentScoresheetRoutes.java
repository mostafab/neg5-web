package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.service.util.RequestHelper;

public class TournamentScoresheetRoutes extends AbstractJsonRoutes {

    @Inject private TournamentScoresheetApi scoresheetApi;
    @Inject private RequestHelper requestHelper;

    @Override
    protected String getBasePath() {
        return "/neg5-api/scoresheets";
    }

    @Override
    public void registerRoutes() {
        post(
                "/convert",
                (request, response) ->
                        scoresheetApi.convertToMatch(
                                requestHelper.readFromRequest(request, ScoresheetDTO.class)));
        post(
                "/submit",
                (request, response) ->
                        scoresheetApi.submitScoresheet(
                                requestHelper.readFromRequest(request, ScoresheetDTO.class)));
    }
}
