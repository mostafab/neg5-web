package org.neg5.controllers;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentCollaboratorApi;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPlayerApi;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTossupValueApi;
import org.neg5.TournamentDTO;
import org.neg5.TournamentTossupValueDTO;
import org.neg5.UpdateTournamentRequestDTO;
import neg5.userData.CurrentUserContext;
import org.neg5.core.QBJGsonProvider;
import org.neg5.enums.TournamentAccessLevel;
import org.neg5.managers.stats.QBJManager;
import org.neg5.util.RequestHelper;

import java.util.List;

public class TournamentController extends AbstractJsonController {

    @Inject private TournamentApi tournamentManager;
    @Inject private TournamentTeamApi tournamentTeamManager;
    @Inject private TournamentPlayerApi tournamentPlayerApi;
    @Inject private TournamentMatchApi tournamentMatchManager;
    @Inject private TournamentPhaseApi tournamentPhaseManager;
    @Inject private TournamentTossupValueApi tournamentTossupValueManager;
    @Inject private TournamentCollaboratorApi tournamentCollaboratorManager;
    @Inject private TournamentRulesApi tournamentRulesManager;

    @Inject private CurrentUserContext currentUserContext;
    @Inject private TournamentAccessManager accessManager;

    @Inject private QBJManager qbjManager;
    @Inject private RequestHelper requestHelper;
    @Inject private QBJGsonProvider qbjGsonProvider;

    private static final String QBJ_CONTENT_TYPE = "application/vnd.quizbowl.qbj+json";

    @Override
    protected String getBasePath() {
        return "/neg5-api/tournaments";
    }

    @Override
    public void registerRoutes() {
        get("", (request, response) -> {
           String userId = currentUserContext.getUserDataOrThrow().getUsername();
           return tournamentCollaboratorManager.getUserTournaments(userId);
        });

        get("/:id", (request, response)
                -> tournamentManager.get(request.params("id")));
        put("/:id", (request, response) -> {
            accessManager.requireAccessLevel(
                    request.params("id"),
                    TournamentAccessLevel.OWNER
            );
            UpdateTournamentRequestDTO updateRequest = requestHelper
                    .readFromRequest(request, UpdateTournamentRequestDTO.class);
            return tournamentManager.update(request.params("id"), updateRequest);
        });

        get("/:id/teams", (request, response)
                -> tournamentTeamManager.findAllByTournamentId(request.params("id")));
        get("/:id/players", (request, response)
                -> tournamentPlayerApi.findAllByTournamentId(request.params("id")));
        get("/:id/matches", (request, response)
                -> tournamentMatchManager.findAllByTournamentId(request.params("id")));
        get("/:id/phases", (request, response)
                -> tournamentPhaseManager.findAllByTournamentId(request.params("id")));
        get("/:id/tossupValues", (request, response)
                -> tournamentTossupValueManager.findAllByTournamentId(request.params("id")));
        get("/:id/collaborators", (request, response)
                -> tournamentCollaboratorManager.findAllByTournamentId(request.params("id")));
        get("/:id/rules", (request, response) -> tournamentRulesManager.getForTournament(request.params("id")));
        get("/:id/qbj", (request, response) -> {
            response.type(QBJ_CONTENT_TYPE);
            return qbjManager.getQbj(request.params("id"));
            }, obj -> qbjGsonProvider.get().toJson(obj)
        );

        post("", (request, response) -> {
            TournamentDTO tournament = requestHelper.readFromRequest(request, TournamentDTO.class);
            return tournamentManager.create(tournament);
        });

        post("/:id/tossupValues", (request, response) -> {
            accessManager.requireAccessLevel(
                    request.params("id"),
                    TournamentAccessLevel.OWNER
            );
            List<TournamentTossupValueDTO> values = requestHelper.readFromRequest(
                    request,
                    new TypeToken<List<TournamentTossupValueDTO>>(){}.getType()
            );
            return tournamentManager.updateTournamentTossupValues(
                    request.params("id"),
                    values
            );
        });
    }
}
