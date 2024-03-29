package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentCollaboratorApi;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPlayerApi;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentRulesDTO;
import neg5.domain.api.TournamentSchedulingApi;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamGroupApi;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.api.UpdateTournamentRequestDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.exports.qbj.api.QBJGsonProvider;
import neg5.exports.qbj.api.QbjApi;
import neg5.exports.qbj.api.QbjRootDTO;
import neg5.service.util.RequestHelper;
import neg5.userData.CurrentUserContext;

public class TournamentRoutes extends AbstractJsonRoutes {

    @Inject private TournamentApi tournamentManager;
    @Inject private TournamentTeamApi tournamentTeamManager;
    @Inject private TournamentPlayerApi tournamentPlayerApi;
    @Inject private TournamentMatchApi tournamentMatchManager;
    @Inject private TournamentPhaseApi tournamentPhaseManager;
    @Inject private TournamentTossupValueApi tournamentTossupValueManager;
    @Inject private TournamentCollaboratorApi tournamentCollaboratorManager;
    @Inject private TournamentRulesApi tournamentRulesManager;
    @Inject private TournamentScoresheetApi scoresheetApi;
    @Inject private TournamentSchedulingApi schedulingApi;
    @Inject private TournamentTeamGroupApi teamGroupApi;

    @Inject private CurrentUserContext currentUserContext;
    @Inject private TournamentAccessManager accessManager;

    @Inject private QbjApi qbjManager;
    @Inject private RequestHelper requestHelper;
    @Inject private QBJGsonProvider qbjGsonProvider;

    private static final String QBJ_CONTENT_TYPE = "application/vnd.quizbowl.qbj+json";

    @Override
    protected String getBasePath() {
        return "/neg5-api/tournaments";
    }

    @Override
    public void registerRoutes() {
        get(
                "",
                (request, response) -> {
                    String userId = currentUserContext.getUserDataOrThrow().getUsername();
                    return tournamentCollaboratorManager.getUserTournaments(userId);
                });

        get("/:id", (request, response) -> tournamentManager.get(request.params("id")));
        put(
                "/:id",
                (request, response) -> {
                    accessManager.requireAccessLevel(
                            request.params("id"), TournamentAccessLevel.OWNER);
                    UpdateTournamentRequestDTO updateRequest =
                            requestHelper.readFromRequest(
                                    request, UpdateTournamentRequestDTO.class);
                    return tournamentManager.update(request.params("id"), updateRequest);
                });

        get(
                "/:id/teams",
                (request, response) ->
                        tournamentTeamManager.findAllByTournamentId(request.params("id")));
        get(
                "/:id/team-groups",
                (request, response) -> teamGroupApi.findAllByTournamentId(request.params("id")));
        get(
                "/:id/players",
                (request, response) ->
                        tournamentPlayerApi.findAllByTournamentId(request.params("id")));
        get(
                "/:id/matches",
                (request, response) ->
                        tournamentMatchManager.findAllByTournamentId(request.params("id")));
        get(
                "/:id/scoresheets",
                (request, response) -> {
                    accessManager.requireAccessLevel(
                            request.params("id"), TournamentAccessLevel.COLLABORATOR);
                    return scoresheetApi.findAllByTournamentId(request.params("id"));
                });
        get(
                "/:id/schedules",
                (request, response) -> {
                    return schedulingApi.findAllByTournamentId(request.params("id"));
                });
        get(
                "/:id/phases",
                (request, response) ->
                        tournamentPhaseManager.findAllByTournamentId(request.params("id")));
        get(
                "/:id/tossupValues",
                (request, response) ->
                        tournamentTossupValueManager.findAllByTournamentId(request.params("id")));
        get(
                "/:id/collaborators",
                (request, response) ->
                        tournamentCollaboratorManager.findAllByTournamentId(request.params("id")));
        get(
                "/:id/rules",
                (request, response) ->
                        tournamentRulesManager.getForTournament(request.params("id")));
        get(
                "/:id/qbj",
                (request, response) -> {
                    QbjRootDTO result = qbjManager.exportToQbjFormat(request.params("id"));
                    String name = tournamentManager.get(request.params("id")).getName();
                    response.type(QBJ_CONTENT_TYPE);
                    response.header(
                            "Content-Disposition",
                            String.format(
                                    "attachment; filename=%s.qbj", name.replaceAll(" ", "_")));

                    return result;
                },
                obj -> qbjGsonProvider.get().toJson(obj));
        get(
                "/:id/permissions",
                (request, response) -> {
                    String userId = currentUserContext.getUserDataOrThrow().getUsername();
                    return accessManager.getUserPermissions(userId, request.params("id"));
                });

        put(
                "/:id/rules",
                (request, response) -> {
                    String tournamentId = request.params("id");
                    accessManager.requireAccessLevel(tournamentId, TournamentAccessLevel.OWNER);
                    TournamentRulesDTO rules =
                            requestHelper.readFromRequest(request, TournamentRulesDTO.class);
                    return tournamentRulesManager.update(tournamentId, rules);
                });

        post(
                "",
                (request, response) -> {
                    TournamentDTO tournament =
                            requestHelper.readFromRequest(request, TournamentDTO.class);
                    return tournamentManager.create(tournament);
                });
    }
}
