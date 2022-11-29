package neg5.domain.api;

import org.neg5.TournamentTossupValueDTO;
import org.neg5.data.embeddables.TournamentTossupValueId;

import java.util.Set;

public interface TournamentTossupValueApi extends DomainObjectApiLayer<TournamentTossupValueDTO, TournamentTossupValueId> {

    void deleteAllFromTournament(String tournamentId);

    Set<TournamentTossupValueDTO> getDefaultTournamentValues();
}
