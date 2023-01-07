package neg5.domain.api;

public interface TournamentScoresheetApi {

    TournamentMatchDTO convertToMatch(ScoresheetDTO scoresheet);

    TournamentMatchDTO submitScoresheet(ScoresheetDTO scoresheet);
}
