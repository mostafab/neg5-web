package neg5.accessManager.api;

import neg5.domain.api.enums.TournamentAccessLevel;

import javax.annotation.Nonnull;

public interface TournamentAccessManager {

    void requireAccessLevel(@Nonnull String tournamentId,
                            @Nonnull TournamentAccessLevel requiredAccessLevel) throws TournamentAccessException;

    TournamentAccessLevel getUserAccessLevelToTournament(@Nonnull String tournamentId, @Nonnull String userId);
}
