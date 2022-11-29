package org.neg5.controllers;

import com.google.inject.Inject;
import neg5.domain.api.TournamentPlayerApi;
import org.eclipse.jetty.http.HttpStatus;
import org.neg5.TournamentPlayerDTO;
import org.neg5.enums.TournamentAccessLevel;
import org.neg5.accessManager.TournamentAccessManager;
import org.neg5.util.RequestHelper;
import spark.Request;
import spark.Response;

public class PlayerController extends AbstractJsonController {

    @Inject private TournamentPlayerApi tournamentPlayerApi;
    @Inject private TournamentAccessManager tournamentAccessManager;
    @Inject private RequestHelper requestHelper;

    @Override
    protected String getBasePath() {
        return "/neg5-api/players";
    }

    @Override
    public void registerRoutes() {
        get("/:id", (req, res) -> tournamentPlayerApi.get(req.params("id")));
        put("/:id", (request, response) -> {
            validatePlayerEditPermissions(request);
            TournamentPlayerDTO player = requestHelper.readFromRequest(request, TournamentPlayerDTO.class);
            player.setId(request.params("id"));
            return tournamentPlayerApi.update(player);
        });
        delete("/:id", (request, response) -> {
            validatePlayerEditPermissions(request);
            tournamentPlayerApi.delete(request.params("id"));
            response.status(HttpStatus.NO_CONTENT_204);
            return "";
        });

        post("", this::createPlayer);
    }

    private void validatePlayerEditPermissions(Request request) {
        TournamentPlayerDTO original = tournamentPlayerApi.get(request.params("id"));
        tournamentAccessManager.requireAccessLevel(
                original.getTournamentId(),
                TournamentAccessLevel.ADMIN
        );
    }

    private TournamentPlayerDTO createPlayer(Request request, Response response) {
        TournamentPlayerDTO playerDTO = requestHelper.readFromRequest(request, TournamentPlayerDTO.class);
        tournamentAccessManager.requireAccessLevel(
                playerDTO.getTournamentId(),
                TournamentAccessLevel.COLLABORATOR
        );
        return tournamentPlayerApi.create(playerDTO);
    }
}
