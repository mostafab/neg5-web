package neg5.domain.api;

import java.util.List;

public interface TournamentApi extends DomainObjectApiLayer<TournamentDTO, String> {

    TournamentDTO update(String tournamentId,
           UpdateTournamentRequestDTO updateTournamentRequest);

    List<TournamentDTO> getTournamentsOwnedByUser(String userId);

    List<TournamentTossupValueDTO> updateTournamentTossupValues(String tournamentId,
                                                                List<TournamentTossupValueDTO> tossupValues);
}
