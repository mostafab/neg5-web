package neg5.domain.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TournamentMatchApi extends DomainObjectApiLayer<TournamentMatchDTO, String> {

    Set<String> getMatchIdsByTournament(String tournamentId);

    List<TournamentMatchDTO> findAllByTournamentAndPhase(String tournamentId, String phaseId);

    Map<String, List<TournamentMatchDTO>> groupMatchesByTeams(String tournamentId,
                                                              String phaseId);
}
