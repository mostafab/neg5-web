package neg5.domain.api;

public interface TournamentScoresheetApi
        extends DomainObjectApiLayer<TournamentScoresheetDTO, Long> {

    TournamentScoresheetDTO createOrUpdateDraft(TournamentScoresheetDTO scoresheet);

    TournamentMatchDTO convertToMatch(TournamentScoresheetDTO scoresheet);

    TournamentMatchDTO submitScoresheet(TournamentScoresheetDTO scoresheet);
}
