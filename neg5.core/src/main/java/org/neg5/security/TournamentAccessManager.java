package org.neg5.security;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.neg5.core.CurrentUserContext;
import org.neg5.core.UserData;
import org.neg5.enums.TournamentAccessLevel;
import org.neg5.managers.TournamentCollaboratorManager;
import org.neg5.managers.TournamentManager;

import javax.annotation.Nonnull;
import java.util.Optional;

@Singleton
public class TournamentAccessManager {

    @Inject private CurrentUserContext currentUserContext;

    @Inject private TournamentCollaboratorManager collaboratorManager;
    @Inject private TournamentManager tournamentManager;

    public void requireAtLeastLevel(@Nonnull String tournamentId,
                                    @Nonnull TournamentAccessLevel requiredAccessLevel) {
        TournamentAccessLevel currentLevel = getCurrentUserAccessLevel(tournamentId);
        if (currentLevel.getLevel() < requiredAccessLevel.getLevel()) {
            throw new TournamentAccessException(tournamentId,
                    "User must have at least " + requiredAccessLevel + " access to tournament " + tournamentId);
        }
    }

    private TournamentAccessLevel getCurrentUserAccessLevel(String tournamentId) {
        Optional<UserData> userData = currentUserContext.getUserData();
        if (!userData.isPresent()) {
            return TournamentAccessLevel.NONE;
        }
        String username = userData.get().getUsername();
        if (userIsDirector(username, tournamentId)) {
            return TournamentAccessLevel.OWNER;
        }
        return collaboratorManager
            .getByTournamentAndUsername(tournamentId, username)
            .map(collaborator ->
                Boolean.TRUE.equals(collaborator.getIsAdmin())
                    ? TournamentAccessLevel.ADMIN
                    : TournamentAccessLevel.COLLABORATOR
            )
            .orElse(TournamentAccessLevel.NONE);
    }

    private boolean userIsDirector(String username, String tournamentId) {
        return username.equals(tournamentManager.get(tournamentId).getDirectorId());
    }
}
