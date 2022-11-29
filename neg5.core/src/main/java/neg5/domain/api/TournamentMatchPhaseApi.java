package neg5.domain.api;

import org.neg5.TournamentMatchPhaseDTO;
import org.neg5.data.embeddables.MatchPhaseId;

import java.util.List;
import java.util.Set;

public interface TournamentMatchPhaseApi extends DomainObjectApiLayer<TournamentMatchPhaseDTO, MatchPhaseId> {

    List<TournamentMatchPhaseDTO> associateMatchWithPhases(Set<String> phaseIds,
                                                           String matchId,
                                                           String tournamentId);
}
