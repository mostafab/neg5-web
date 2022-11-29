package neg5.accessManager.api;

import org.neg5.enums.TournamentAccessLevel;

import javax.annotation.Nonnull;

public interface TournamentAccessManager {

    void requireAccessLevel(@Nonnull String tournamentId,
                            @Nonnull TournamentAccessLevel requiredAccessLevel) throws TournamentAccessException;

    TournamentAccessLevel getUserAccessLevelToTournament(@Nonnull String tournamentId, @Nonnull String userId);
}
