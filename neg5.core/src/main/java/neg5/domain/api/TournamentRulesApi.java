package neg5.domain.api;

import org.neg5.TournamentRulesDTO;

public interface TournamentRulesApi {

    TournamentRulesDTO getForTournament(String tournamentId);
}
