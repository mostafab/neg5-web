package neg5.domain.api;

import org.neg5.TournamentPhaseDTO;

public interface TournamentPhaseApi extends DomainObjectApiLayer<TournamentPhaseDTO, String> {

    TournamentPhaseDTO createDefaultPhase(String tournamentId);
}
