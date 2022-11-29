package neg5.api;

import org.neg5.TournamentMatchDTO;

import java.util.List;
import java.util.Set;

public interface TournamentMatchApi extends ObjectApiLayer<TournamentMatchDTO, String> {

    Set<String> getMatchIdsByTournament(String tournamentId);

    List<TournamentMatchDTO> findAllByTournamentAndPhase(String tournamentId, String phaseId);
}
