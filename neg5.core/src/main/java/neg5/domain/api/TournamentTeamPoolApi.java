package neg5.domain.api;

import org.neg5.TournamentTeamPoolDTO;
import org.neg5.data.embeddables.TournamentTeamPoolId;

import java.util.List;
import java.util.Set;

public interface TournamentTeamPoolApi extends DomainObjectApiLayer<TournamentTeamPoolDTO, TournamentTeamPoolId> {

    List<TournamentTeamPoolDTO> associateTeamWithPools(Set<String> divisionIds,
                                                       String teamId,
                                                       String tournamentId);
}
