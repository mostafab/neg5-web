package org.neg5.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentCollaboratorApi;
import org.eclipse.jetty.http.HttpStatus;
import org.neg5.TournamentCollaboratorDTO;
import org.neg5.enums.TournamentAccessLevel;
import org.neg5.util.RequestHelper;
import spark.Request;

public class TournamentCollaboratorController extends AbstractJsonController {

    private final TournamentCollaboratorApi collaboratorManager;
    private final TournamentAccessManager accessManager;
    private final RequestHelper requestHelper;

    @Inject
    public TournamentCollaboratorController(TournamentCollaboratorApi collaboratorManager,
                                            TournamentAccessManager accessManager,
                                            RequestHelper requestHelper) {
        this.collaboratorManager = collaboratorManager;
        this.accessManager = accessManager;
        this.requestHelper = requestHelper;
    }

    @Override
    public void registerRoutes() {
        post("", (request, response) -> collaboratorManager.addOrUpdateCollaborator(verifyEditAccessAndParseBody(request)));
        delete("", (request, response) -> {
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
        TournamentCollaboratorDTO collaborator = requestHelper
                .readFromRequest(request, TournamentCollaboratorDTO.class);
        accessManager.requireAccessLevel(
                collaborator.getTournamentId(),
                TournamentAccessLevel.OWNER
        );
        return collaborator;
    }
}
