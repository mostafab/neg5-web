package neg5.service.controllers;

import com.google.inject.Inject;
import java.util.List;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.BatchTeamPoolUpdatesDTO;
import neg5.domain.api.TeamPoolAssignmentsDTO;
import neg5.domain.api.TournamentPoolApi;
import neg5.domain.api.TournamentPoolDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamPoolApi;
import neg5.domain.api.TournamentTeamsPoolsDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;
import neg5.transactions.TransactionHelper;

public class TournamentTeamPoolRoutes extends AbstractJsonRoutes {

    @Inject private TournamentTeamPoolApi teamPoolApi;
    @Inject private TournamentTeamApi teamApi;
    @Inject private TournamentPoolApi poolApi;
    @Inject private RequestHelper requestHelper;
    @Inject private TournamentAccessManager accessManager;
    @Inject private TransactionHelper transactionHelper;

    @Override
    protected String getBasePath() {
        return "/neg5-api/team-pools";
    }

    @Override
    public void registerRoutes() {
        post(
                "",
                (request, response) -> {
                    TeamPoolAssignmentsDTO assignments =
                            requestHelper.readFromRequest(request, TeamPoolAssignmentsDTO.class);
                    validateHasAccessToEditTeam(assignments);
                    return teamPoolApi.associateTeamWithPools(assignments);
                });
        post(
                "/batch",
                (request, response) -> {
                    BatchTeamPoolUpdatesDTO updates =
                            requestHelper.readFromRequest(request, BatchTeamPoolUpdatesDTO.class);
                    updates.getAssignments().forEach(this::validateHasAccessToEditTeam);
                    if (updates.getPoolsToRemove() != null) {
                        List<TournamentPoolDTO> poolsToDelete =
                                poolApi.get(updates.getPoolsToRemove());
                        poolsToDelete.forEach(
                                pool -> {
                                    accessManager.requireAccessLevel(
                                            pool.getTournamentId(), TournamentAccessLevel.ADMIN);
                                });
                    }
                    return transactionHelper.runInTransaction(
                            () -> {
                                List<TournamentTeamsPoolsDTO> pools =
                                        teamPoolApi.batchAssociateWithPools(updates);
                                if (updates.getPoolsToRemove() != null) {
                                    updates.getPoolsToRemove()
                                            .forEach(poolId -> poolApi.delete(poolId));
                                }
                                return pools;
                            });
                });
    }

    private void validateHasAccessToEditTeam(TeamPoolAssignmentsDTO assignment) {
        TournamentTeamDTO team = teamApi.get(assignment.getTeamId());
        accessManager.requireAccessLevel(team.getTournamentId(), TournamentAccessLevel.ADMIN);
    }
}
