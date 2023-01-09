package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.api.TournamentScoresheetDTO;
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
                "",
                (request, response) -> {
                    TournamentScoresheetDTO scoresheet =
                            requestHelper.readFromRequest(request, TournamentScoresheetDTO.class);
                    ensureHasPermissions(scoresheet);
                    return scoresheetApi.createOrUpdateDraft(scoresheet);
                });
        post(
                "/convert",
                (request, response) ->
                        scoresheetApi.convertToMatch(
                                requestHelper.readFromRequest(
                                        request, TournamentScoresheetDTO.class)));
        post(
                "/submit",
                (request, response) ->
                        scoresheetApi.submitScoresheet(
                                requestHelper.readFromRequest(
                                        request, TournamentScoresheetDTO.class)));
    }

    private void ensureHasPermissions(Long scoresheetId) {
        TournamentScoresheetDTO scoresheet = scoresheetApi.get(scoresheetId);
        ensureHasPermissions(scoresheet);
    }

    private void ensureHasPermissions(TournamentScoresheetDTO scoresheet) {
        accessManager.requireAccessLevel(
                scoresheet.getTournamentId(), TournamentAccessLevel.COLLABORATOR);
    }
}
