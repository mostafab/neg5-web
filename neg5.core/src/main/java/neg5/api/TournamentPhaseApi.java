package neg5.api;

import org.neg5.TournamentPhaseDTO;

public interface TournamentPhaseApi extends ObjectApiLayer<TournamentPhaseDTO, String> {

    TournamentPhaseDTO createDefaultPhase(String tournamentId);
}
