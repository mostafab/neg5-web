package org.neg5.controllers;

import com.google.inject.Inject;
import org.neg5.TournamentPhaseDTO;
import org.neg5.enums.TournamentAccessLevel;
import org.neg5.managers.TournamentPhaseManager;
import org.neg5.accessManager.TournamentAccessManager;
import org.neg5.util.RequestHelper;

public class TournamentPhaseController extends AbstractJsonController {

    private final TournamentPhaseManager phaseManager;
    private final RequestHelper requestHelper;
    private final TournamentAccessManager accessManager;

    @Inject
    public TournamentPhaseController(TournamentPhaseManager phaseManager,
                                     RequestHelper requestHelper,
                                     TournamentAccessManager accessManager) {
        this.phaseManager = phaseManager;
        this.requestHelper = requestHelper;
        this.accessManager = accessManager;
    }

    @Override
    public void registerRoutes() {
        post("", (request, response) -> {
            TournamentPhaseDTO phase = requestHelper.readFromRequest(request, TournamentPhaseDTO.class);
            accessManager.requireAccessLevel(phase.getTournamentId(), TournamentAccessLevel.OWNER);
            return phaseManager.create(phase);
        });

        put("/:id", (request, response) -> {
            TournamentPhaseDTO original = phaseManager.get(request.params("id"));
            accessManager.requireAccessLevel(
                    original.getTournamentId(),
                    TournamentAccessLevel.OWNER
            );
            TournamentPhaseDTO phase = requestHelper.readFromRequest(request, TournamentPhaseDTO.class);
            phase.setId(request.params("id"));
            return phaseManager.update(phase);
        });
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/phases";
    }
}
