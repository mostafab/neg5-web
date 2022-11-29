package neg5.api;

import org.neg5.TournamentDTO;
import org.neg5.TournamentTossupValueDTO;
import org.neg5.UpdateTournamentRequestDTO;

import java.util.List;

public interface TournamentApi extends ObjectApiLayer<TournamentDTO, String> {

    TournamentDTO update(String tournamentId,
           UpdateTournamentRequestDTO updateTournamentRequest);

    List<TournamentDTO> getTournamentsOwnedByUser(String userId);

    List<TournamentTossupValueDTO> updateTournamentTossupValues(String tournamentId,
                                                                List<TournamentTossupValueDTO> tossupValues);
}
