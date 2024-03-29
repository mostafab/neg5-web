package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentCollaboratorApi;
import neg5.domain.api.TournamentCollaboratorDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;

public class TournamentCollaboratorRoutes extends AbstractJsonRoutes {

    private final TournamentCollaboratorApi collaboratorManager;
    private final TournamentAccessManager accessManager;
    private final RequestHelper requestHelper;

    @Inject
    public TournamentCollaboratorRoutes(
            TournamentCollaboratorApi collaboratorManager,
            TournamentAccessManager accessManager,
            RequestHelper requestHelper) {
        this.collaboratorManager = collaboratorManager;
        this.accessManager = accessManager;
        this.requestHelper = requestHelper;
    }

    @Override
    public void registerRoutes() {
        post(
                "",
                (request, response) ->
                        collaboratorManager.addOrUpdateCollaborator(
                                verifyEditAccessAndParseBody(request)));
        post(
                "/delete",
                (request, response) -> {
                    TournamentCollaboratorDTO collaborator = verifyEditAccessAndParseBody(request);
                    collaboratorManager.delete(collaborator);
                    response.status(HttpStatus.NO_CONTENT_204);
                    return "";
                });
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/collaborators";
    }

    private TournamentCollaboratorDTO verifyEditAccessAndParseBody(Request request) {
        TournamentCollaboratorDTO collaborator =
                requestHelper.readFromRequest(request, TournamentCollaboratorDTO.class);
        accessManager.requireAccessLevel(
                collaborator.getTournamentId(), TournamentAccessLevel.OWNER);
        return collaborator;
    }
}
