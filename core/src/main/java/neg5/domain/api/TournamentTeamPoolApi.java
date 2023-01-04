package neg5.domain.api;

import java.util.List;
import javax.annotation.Nonnull;
import neg5.domain.impl.entities.compositeIds.TournamentTeamPoolId;

public interface TournamentTeamPoolApi
        extends DomainObjectApiLayer<TournamentTeamPoolDTO, TournamentTeamPoolId> {

    List<TournamentTeamPoolDTO> associateTeamWithPools(@Nonnull TeamPoolAssignmentsDTO assignment);

    List<TournamentTeamsPoolsDTO> batchAssociateWithPools(@Nonnull BatchTeamPoolUpdatesDTO updates);
}
