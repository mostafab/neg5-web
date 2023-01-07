package neg5.domain.api;

public interface TournamentScoresheetApi extends DomainObjectApiLayer<ScoresheetDTO, Long> {

    TournamentMatchDTO convertToMatch(ScoresheetDTO scoresheet);

    TournamentMatchDTO submitScoresheet(ScoresheetDTO scoresheet);
}
