package neg5.api;

import org.neg5.TournamentMatchDTO;
import org.neg5.TournamentPlayerDTO;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface TournamentPlayerApi extends DomainObjectApiLayer<TournamentPlayerDTO, String> {

    List<TournamentPlayerDTO> findByTeamId(@Nonnull String teamId);

    Map<String, List<TournamentMatchDTO>> groupMatchesByPlayers(String tournamentId, String phaseId);
}
