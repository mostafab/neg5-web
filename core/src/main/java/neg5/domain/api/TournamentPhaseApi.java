package neg5.domain.api;

public interface TournamentPhaseApi extends DomainObjectApiLayer<TournamentPhaseDTO, String> {

    TournamentPhaseDTO createDefaultPhase(String tournamentId);
}
