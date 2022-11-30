package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPhaseDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;

public class TournamentPhaseController extends AbstractJsonController {

    private final TournamentPhaseApi phaseManager;
    private final RequestHelper requestHelper;
    private final TournamentAccessManager accessManager;

    @Inject
    public TournamentPhaseController(
            TournamentPhaseApi phaseManager,
            RequestHelper requestHelper,
            TournamentAccessManager accessManager) {
        this.phaseManager = phaseManager;
        this.requestHelper = requestHelper;
        this.accessManager = accessManager;
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/phases";
    }

    @Override
    public void registerRoutes() {
        post(
                "",
                (request, response) -> {
                    TournamentPhaseDTO phase =
                            requestHelper.readFromRequest(request, TournamentPhaseDTO.class);
                    accessManager.requireAccessLevel(
                            phase.getTournamentId(), TournamentAccessLevel.OWNER);
                    return phaseManager.create(phase);
                });

        put(
                "/:id",
                (request, response) -> {
                    TournamentPhaseDTO original = phaseManager.get(request.params("id"));
                    accessManager.requireAccessLevel(
                            original.getTournamentId(), TournamentAccessLevel.OWNER);
                    TournamentPhaseDTO phase =
                            requestHelper.readFromRequest(request, TournamentPhaseDTO.class);
                    phase.setId(request.params("id"));
                    return phaseManager.update(phase);
                });
    }
}
