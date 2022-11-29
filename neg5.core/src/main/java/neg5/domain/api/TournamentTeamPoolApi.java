package neg5.domain.api;

import neg5.domain.impl.entities.compositeIds.TournamentTeamPoolId;

import java.util.List;
import java.util.Set;

public interface TournamentTeamPoolApi extends DomainObjectApiLayer<TournamentTeamPoolDTO, TournamentTeamPoolId> {

    List<TournamentTeamPoolDTO> associateTeamWithPools(Set<String> divisionIds,
                                                       String teamId,
                                                       String tournamentId);
}
