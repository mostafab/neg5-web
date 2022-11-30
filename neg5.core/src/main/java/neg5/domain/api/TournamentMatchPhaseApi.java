package neg5.domain.api;

import java.util.List;
import java.util.Set;
import neg5.domain.impl.entities.compositeIds.MatchPhaseId;

public interface TournamentMatchPhaseApi
        extends DomainObjectApiLayer<TournamentMatchPhaseDTO, MatchPhaseId> {

    List<TournamentMatchPhaseDTO> associateMatchWithPhases(
            Set<String> phaseIds, String matchId, String tournamentId);
}
