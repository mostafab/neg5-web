package org.neg5.controllers;

import com.google.inject.Inject;
import neg5.api.TournamentPoolApi;
import org.neg5.TournamentPoolDTO;
import org.neg5.enums.TournamentAccessLevel;
import org.neg5.accessManager.TournamentAccessManager;
import org.neg5.util.RequestHelper;

public class TournamentPoolController extends AbstractJsonController {

    private final TournamentPoolApi poolManager;
    private final RequestHelper requestHelper;
    private final TournamentAccessManager accessManager;

    @Inject
    public TournamentPoolController(TournamentPoolApi poolManager,
                                    RequestHelper requestHelper,
                                    TournamentAccessManager accessManager) {
        this.poolManager = poolManager;
        this.requestHelper = requestHelper;
        this.accessManager = accessManager;
    }

    @Override
    public void registerRoutes() {
        post("", (request, response) -> {
            TournamentPoolDTO pool = requestHelper.readFromRequest(request, TournamentPoolDTO.class);
            accessManager.requireAccessLevel(pool.getTournamentId(), TournamentAccessLevel.OWNER);
            return poolManager.create(pool);
        });

        put("/:id", (request, response) -> {
            TournamentPoolDTO original = poolManager.get(request.params("id"));
            accessManager.requireAccessLevel(
                    original.getTournamentId(),
                    TournamentAccessLevel.OWNER
            );
            TournamentPoolDTO pool = requestHelper.readFromRequest(request, TournamentPoolDTO.class);
            pool.setId(request.params("id"));
            return poolManager.update(pool);
        });
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/pools";
    }
}
