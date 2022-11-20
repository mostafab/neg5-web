package org.neg5.controllers;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import org.neg5.TournamentDTO;
import org.neg5.TournamentTossupValueDTO;
import org.neg5.UpdateTournamentRequestDTO;
import org.neg5.core.CurrentUserContext;
import org.neg5.core.QBJGsonProvider;
import org.neg5.enums.TournamentAccessLevel;
import org.neg5.managers.TournamentCollaboratorManager;
import org.neg5.managers.TournamentManager;
import org.neg5.managers.TournamentMatchManager;
import org.neg5.managers.TournamentPhaseManager;
import org.neg5.managers.TournamentPlayerManager;
import org.neg5.managers.TournamentRulesManager;
import org.neg5.managers.TournamentTeamManager;
import org.neg5.managers.TournamentTossupValueManager;
import org.neg5.managers.stats.QBJManager;
import org.neg5.accessManager.TournamentAccessManager;
import org.neg5.util.RequestHelper;

import java.util.List;

public class TournamentController extends AbstractJsonController {

    @Inject private TournamentManager tournamentManager;
    @Inject private TournamentTeamManager tournamentTeamManager;
    @Inject private TournamentPlayerManager tournamentPlayerManager;
    @Inject private TournamentMatchManager tournamentMatchManager;
    @Inject private TournamentPhaseManager tournamentPhaseManager;
    @Inject private TournamentTossupValueManager tournamentTossupValueManager;
    @Inject private TournamentCollaboratorManager tournamentCollaboratorManager;
    @Inject private TournamentRulesManager tournamentRulesManager;

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
                -> tournamentPlayerManager.findAllByTournamentId(request.params("id")));
        get("/:id/matches", (request, response)
                -> tournamentMatchManager.findAllByTournamentId(request.params("id")));
        get("/:id/phases", (request, response)
                -> tournamentPhaseManager.findAllByTournamentId(request.params("id")));
        get("/:id/tossupValues", (request, response)
                -> tournamentTossupValueManager.findAllByTournamentId(request.params("id")));
        get("/:id/collaborators", (request, response)
                -> tournamentCollaboratorManager.findAllByTournamentId(request.params("id")));
        get("/:id/rules", (request, response) -> tournamentRulesManager.get(request.params("id")));
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
