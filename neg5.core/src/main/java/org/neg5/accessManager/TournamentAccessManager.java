package org.neg5.accessManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentCollaboratorApi;
import neg5.userData.CurrentUserContext;
import neg5.userData.UserData;
import org.neg5.enums.TournamentAccessLevel;

import javax.annotation.Nonnull;
import java.util.Optional;

@Singleton
public class TournamentAccessManager {

    private final CurrentUserContext currentUserContext;

    private final TournamentCollaboratorApi collaboratorManager;
    private final TournamentApi tournamentManager;

    @Inject
    public TournamentAccessManager(CurrentUserContext currentUserContext,
                                   TournamentCollaboratorApi collaboratorManager,
                                   TournamentApi tournamentManager) {
        this.currentUserContext = currentUserContext;
        this.collaboratorManager = collaboratorManager;
        this.tournamentManager = tournamentManager;
    }

    public void requireAccessLevel(@Nonnull String tournamentId,
                                   @Nonnull TournamentAccessLevel requiredAccessLevel) {
        TournamentAccessLevel currentLevel = getCurrentUserAccessLevel(tournamentId);
        if (currentLevel.getLevel() < requiredAccessLevel.getLevel()) {
            throw new TournamentAccessException(tournamentId,
                    "User must have at least " + requiredAccessLevel + " access to tournament " + tournamentId);
        }
    }

    public TournamentAccessLevel getUserAccessLevelToTournament(String tournamentId,
                                                                String userId) {
        if (userIsDirector(userId, tournamentId)) {
            return TournamentAccessLevel.OWNER;
        }
        return collaboratorManager.getByTournamentAndUsername(tournamentId, userId)
                .map(collaborator ->
                        Boolean.TRUE.equals(collaborator.getIsAdmin())
                                ? TournamentAccessLevel.ADMIN
                                : TournamentAccessLevel.COLLABORATOR
                )
                .orElse(TournamentAccessLevel.NONE);
    }

    private TournamentAccessLevel getCurrentUserAccessLevel(String tournamentId) {
        Optional<UserData> userData = currentUserContext.getUserData();
        if (!userData.isPresent()) {
            return TournamentAccessLevel.NONE;
        }
        String username = userData.get().getUsername();
        return getUserAccessLevelToTournament(tournamentId, username);
    }

    private boolean userIsDirector(String username, String tournamentId) {
        return username.equals(tournamentManager.get(tournamentId).getDirectorId());
    }
}
