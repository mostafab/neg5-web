package neg5.accessManager.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.accessManager.api.TournamentAccessException;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentCollaboratorApi;
import neg5.domain.api.TournamentPermissionsDTO;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.userData.CurrentUserContext;
import neg5.userData.UserData;

@Singleton
public class TournamentAccessManagerImpl implements TournamentAccessManager {

    private final CurrentUserContext currentUserContext;

    private final TournamentCollaboratorApi collaboratorManager;
    private final TournamentApi tournamentManager;

    @Inject
    public TournamentAccessManagerImpl(
            CurrentUserContext currentUserContext,
            TournamentCollaboratorApi collaboratorManager,
            TournamentApi tournamentManager) {
        this.currentUserContext = currentUserContext;
        this.collaboratorManager = collaboratorManager;
        this.tournamentManager = tournamentManager;
    }

    @Override
    public TournamentPermissionsDTO getUserPermissions(
            @Nonnull String userId, @Nonnull String tournamentId) {
        TournamentPermissionsDTO result = new TournamentPermissionsDTO();
        result.setTournamentId(tournamentId);
        TournamentAccessLevel accessLevel = getUserAccessLevelToTournament(tournamentId, userId);
        result.setAccessLevel(accessLevel);
        result.setCanEditInfo(accessLevel.isAtLeast(TournamentAccessLevel.OWNER));
        result.setCanEditCollaborators(accessLevel.isAtLeast(TournamentAccessLevel.OWNER));
        result.setCanEditMatches(accessLevel.isAtLeast(TournamentAccessLevel.ADMIN));
        result.setCanEditTeams(accessLevel.isAtLeast(TournamentAccessLevel.ADMIN));
        result.setCanEditPools(accessLevel.isAtLeast(TournamentAccessLevel.ADMIN));
        result.setCanEditRules(accessLevel.isAtLeast(TournamentAccessLevel.OWNER));
        return result;
    }

    @Override
    public void requireAccessLevel(
            @Nonnull String tournamentId, @Nonnull TournamentAccessLevel requiredAccessLevel)
            throws TournamentAccessException {
        TournamentAccessLevel currentLevel = getCurrentUserAccessLevel(tournamentId);
        if (!currentLevel.isAtLeast(requiredAccessLevel)) {
            throw new TournamentAccessException(
                    tournamentId,
                    "User must have at least "
                            + requiredAccessLevel
                            + " access to tournament "
                            + tournamentId);
        }
    }

    @Override
    public TournamentAccessLevel getUserAccessLevelToTournament(
            @Nonnull String tournamentId, @Nonnull String userId) {
        if (userIsDirector(userId, tournamentId)) {
            return TournamentAccessLevel.OWNER;
        }
        return collaboratorManager
                .getByTournamentAndUsername(tournamentId, userId)
                .map(
                        collaborator ->
                                Boolean.TRUE.equals(collaborator.getIsAdmin())
                                        ? TournamentAccessLevel.ADMIN
                                        : TournamentAccessLevel.COLLABORATOR)
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
