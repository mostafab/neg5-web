package neg5.api;

import org.neg5.TournamentTossupValueDTO;
import org.neg5.data.embeddables.TournamentTossupValueId;

import java.util.Set;

public interface TournamentTossupValueApi extends ObjectApiLayer<TournamentTossupValueDTO, TournamentTossupValueId> {

    void deleteAllFromTournament(String tournamentId);

    Set<TournamentTossupValueDTO> getDefaultTournamentValues();
}
