package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class MatchController extends AbstractJsonController {

    @Inject private TournamentMatchApi matchManager;
    @Inject private RequestHelper requestHelper;
    @Inject private TournamentAccessManager tournamentAccessManager;

    @Override
    protected String getBasePath() {
        return "/neg5-api/matches";
    }

    @Override
    public void registerRoutes() {
        get("/:id", this::getMatch);
        put(
                "/:id",
                (request, response) -> {
                    verifyAccessToEditMatch(request);
                    TournamentMatchDTO match =
                            requestHelper.readFromRequest(request, TournamentMatchDTO.class);
                    match.setId(request.params("id"));
                    return matchManager.update(match);
                });
        delete(
                "/:id",
                (request, response) -> {
                    verifyAccessToEditMatch(request);
                    matchManager.delete(request.params("id"));
                    response.status(HttpStatus.NO_CONTENT_204);
                    return "";
                });

        post("", this::createMatch);
    }

    private void verifyAccessToEditMatch(Request request) {
        TournamentMatchDTO original = matchManager.get(request.params("id"));
        tournamentAccessManager.requireAccessLevel(
                original.getTournamentId(), TournamentAccessLevel.ADMIN);
    }

    private TournamentMatchDTO createMatch(Request request, Response response) {
        TournamentMatchDTO tournamentMatchDTO =
                requestHelper.readFromRequest(request, TournamentMatchDTO.class);
        tournamentAccessManager.requireAccessLevel(
                tournamentMatchDTO.getTournamentId(), TournamentAccessLevel.COLLABORATOR);
        return matchManager.create(tournamentMatchDTO);
    }

    private TournamentMatchDTO getMatch(Request request, Response response) {
        return matchManager.get(request.params("id"));
    }
}
