package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;

public class TournamentTossupValueController extends AbstractJsonController {

    @Inject private TournamentTossupValueApi tournamentTossupValueManager;
    @Inject private TournamentAccessManager tournamentAccessManager;
    @Inject private RequestHelper requestHelper;

    @Override
    protected String getBasePath() {
        return "/neg5-api/tossup-values";
    }

    @Override
    public void registerRoutes() {
        post("", (req, res) -> {
            TournamentTossupValueDTO dto = requestHelper.readFromRequest(req, TournamentTossupValueDTO.class);
            tournamentAccessManager.requireAccessLevel(dto.getTournamentId(), TournamentAccessLevel.OWNER);
            return tournamentTossupValueManager.create(dto);
        });
    }
}
