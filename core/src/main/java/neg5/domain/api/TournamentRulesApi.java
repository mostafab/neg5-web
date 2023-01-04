package neg5.domain.api;

import javax.annotation.Nonnull;

public interface TournamentRulesApi {

    TournamentRulesDTO update(@Nonnull String tournamentId, @Nonnull TournamentRulesDTO rules);

    TournamentRulesDTO getForTournament(String tournamentId);
}
