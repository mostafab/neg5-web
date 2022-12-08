package neg5.accessManager.api;

import javax.annotation.Nonnull;
import neg5.domain.api.enums.TournamentAccessLevel;

public interface TournamentAccessManager {

    void requireAccessLevel(
            @Nonnull String tournamentId, @Nonnull TournamentAccessLevel requiredAccessLevel)
            throws TournamentAccessException;

    TournamentAccessLevel getUserAccessLevelToTournament(
            @Nonnull String tournamentId, @Nonnull String userId);
}
