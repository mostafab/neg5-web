package neg5.domain.api;

import java.util.List;
import java.util.Set;
import neg5.domain.impl.entities.compositeIds.TournamentTeamPoolId;

public interface TournamentTeamPoolApi
        extends DomainObjectApiLayer<TournamentTeamPoolDTO, TournamentTeamPoolId> {

    List<TournamentTeamPoolDTO> associateTeamWithPools(
            Set<String> divisionIds, String teamId, String tournamentId);
}
