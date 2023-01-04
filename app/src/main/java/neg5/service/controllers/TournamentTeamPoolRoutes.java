package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TeamPoolAssignmentsDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamPoolApi;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;

public class TournamentTeamPoolRoutes extends AbstractJsonRoutes {

    @Inject private TournamentTeamPoolApi teamPoolApi;
    @Inject private TournamentTeamApi teamApi;
    @Inject private RequestHelper requestHelper;
    @Inject private TournamentAccessManager accessManager;

    @Override
    protected String getBasePath() {
        return "/neg5-api/team-pools";
    }

    @Override
    public void registerRoutes() {
        put(
                "",
                (request, response) -> {
                    TeamPoolAssignmentsDTO assignments =
                            requestHelper.readFromRequest(request, TeamPoolAssignmentsDTO.class);
                    validateHasAccessToEditTeam(assignments);
                    return teamPoolApi.associateTeamWithPools(assignments);
                });
    }

    private void validateHasAccessToEditTeam(TeamPoolAssignmentsDTO assignment) {
        TournamentTeamDTO team = teamApi.get(assignment.getTeamId());
        accessManager.requireAccessLevel(team.getTournamentId(), TournamentAccessLevel.ADMIN);
    }
}
