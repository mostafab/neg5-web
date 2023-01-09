package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;

public class TournamentScoresheetRoutes extends AbstractJsonRoutes {

    @Inject private TournamentScoresheetApi scoresheetApi;
    @Inject private RequestHelper requestHelper;
    @Inject private TournamentAccessManager accessManager;

    @Override
    protected String getBasePath() {
        return "/neg5-api/scoresheets";
    }

    @Override
    public void registerRoutes() {
        get(
                "/:id",
                (request, response) -> {
                    Long id = Long.parseLong(request.params("id"));
                    ensureHasPermissions(id);
                    return scoresheetApi.get(id);
                });
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

    private void ensureHasPermissions(Long scoresheetId) {
        ScoresheetDTO scoresheet = scoresheetApi.get(scoresheetId);
        accessManager.requireAccessLevel(
                scoresheet.getTournamentId(), TournamentAccessLevel.COLLABORATOR);
    }
}
