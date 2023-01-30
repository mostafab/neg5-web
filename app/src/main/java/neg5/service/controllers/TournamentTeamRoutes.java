package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamGroupApi;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class TournamentTeamRoutes extends AbstractJsonRoutes {

    @Inject private TournamentTeamApi teamManager;
    @Inject private TournamentTeamGroupApi teamGroupApi;
    @Inject private TournamentAccessManager tournamentAccessManager;
    @Inject private RequestHelper requestHelper;

    @Override
    protected String getBasePath() {
        return "/neg5-api/teams";
    }

    @Override
    public void registerRoutes() {
        get("/:id", (request, response) -> teamManager.get(request.params("id")));
        put(
                "/:id",
                (request, response) -> {
                    validateHasAccessToEditTeam(request);
                    TournamentTeamDTO team =
                            requestHelper.readFromRequest(request, TournamentTeamDTO.class);
                    team.setId(request.params("id"));
                    return teamManager.update(team);
                });

        delete(
                "/:id",
                (request, response) -> {
                    validateHasAccessToEditTeam(request);
                    teamManager.delete(request.params("id"));
                    response.status(HttpStatus.NO_CONTENT_204);
                    return "";
                });

        post("", this::createTeam);

        post(
                "/group",
                (request, response) -> {
                    TournamentTeamGroupDTO group =
                            requestHelper.readFromRequest(request, TournamentTeamGroupDTO.class);
                    tournamentAccessManager.requireAccessLevel(
                            group.getTournamentId(), TournamentAccessLevel.ADMIN);
                    return teamGroupApi.create(group);
                });
    }

    private void validateHasAccessToEditTeam(Request request) {
        TournamentTeamDTO original = teamManager.get(request.params("id"));
        tournamentAccessManager.requireAccessLevel(
                original.getTournamentId(), TournamentAccessLevel.ADMIN);
    }

    private Object createTeam(Request request, Response response) {
        TournamentTeamDTO team = requestHelper.readFromRequest(request, TournamentTeamDTO.class);
        tournamentAccessManager.requireAccessLevel(
                team.getTournamentId(), TournamentAccessLevel.ADMIN);
        return teamManager.create(team);
    }
}
