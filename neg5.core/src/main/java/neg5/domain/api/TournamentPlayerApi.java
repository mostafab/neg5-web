package neg5.domain.api;

import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

public interface TournamentPlayerApi extends DomainObjectApiLayer<TournamentPlayerDTO, String> {

    List<TournamentPlayerDTO> findByTeamId(@Nonnull String teamId);

    Map<String, List<TournamentMatchDTO>> groupMatchesByPlayers(
            String tournamentId, String phaseId);
}
