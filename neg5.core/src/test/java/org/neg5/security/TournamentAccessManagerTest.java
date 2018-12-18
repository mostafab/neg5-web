package org.neg5.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neg5.TournamentCollaboratorDTO;
import org.neg5.TournamentDTO;
import org.neg5.core.CurrentUserContext;
import org.neg5.core.UserData;

import org.neg5.enums.TournamentAccessLevel;
import org.neg5.managers.TournamentCollaboratorManager;
import org.neg5.managers.TournamentManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TournamentAccessManagerTest {

    @Mock private TournamentCollaboratorManager collaboratorManager;
    @Mock private TournamentManager tournamentManager;
    @Mock private CurrentUserContext currentUserContext;

    @InjectMocks private TournamentAccessManager tournamentAccessManager;

    private static final String TOURNAMENT_ID = "1";

    @Test
    public void testAccessExceptionThrownIfNoUserContextAndRequireLevel() {
        when(currentUserContext.getUserData()).thenReturn(Optional.empty());
        assertThrows(TournamentAccessException.class, () -> {
            tournamentAccessManager.requireAtLeastLevel(TOURNAMENT_ID, TournamentAccessLevel.COLLABORATOR);
        });
    }

    @Test
    public void testAccessExceptionIfUserNotCollaborator() {
        Optional<UserData> userData = Optional.of(new UserData("TEST"));
        when(currentUserContext.getUserData()).thenReturn(userData);
        when(tournamentManager.get(any())).thenReturn(buildTournament());
        when(collaboratorManager.getByTournamentAndUsername(any(), any()))
                .thenReturn(Optional.empty());

        assertThrows(TournamentAccessException.class, () -> {
            tournamentAccessManager.requireAtLeastLevel(TOURNAMENT_ID, TournamentAccessLevel.COLLABORATOR);
        });
    }

    @Test
    public void testAccessExceptionThrownIfUserCollaboratorButRequiresAdminAccess() {
        Optional<UserData> userData = Optional.of(new UserData("TEST"));
        when(currentUserContext.getUserData()).thenReturn(userData);
        when(tournamentManager.get(any())).thenReturn(buildTournament());
        when(collaboratorManager.getByTournamentAndUsername(any(), any()))
                .thenReturn(Optional.of(buildCollaborator(false)));

        assertThrows(TournamentAccessException.class, () -> {
            tournamentAccessManager.requireAtLeastLevel(TOURNAMENT_ID, TournamentAccessLevel.ADMIN);
        });
    }

    @Test
    public void testAccessExceptionThrownIfUserAdmimButRequiresDirectorAccess() {
        Optional<UserData> userData = Optional.of(new UserData("TEST"));
        when(currentUserContext.getUserData()).thenReturn(userData);
        when(tournamentManager.get(any())).thenReturn(buildTournament());
        when(collaboratorManager.getByTournamentAndUsername(any(), any()))
                .thenReturn(Optional.of(buildCollaborator(true)));

        assertThrows(TournamentAccessException.class, () -> {
            tournamentAccessManager.requireAtLeastLevel(TOURNAMENT_ID, TournamentAccessLevel.OWNER);
        });
    }

    @Test
    public void testCanAccessIfHigherAccessLevelThanRequired() {
        Optional<UserData> userData = Optional.of(new UserData("TEST"));
        when(currentUserContext.getUserData()).thenReturn(userData);
        when(tournamentManager.get(any())).thenReturn(buildTournament());
        when(collaboratorManager.getByTournamentAndUsername(any(), any()))
                .thenReturn(Optional.of(buildCollaborator(true)));

        tournamentAccessManager.requireAtLeastLevel(TOURNAMENT_ID, TournamentAccessLevel.COLLABORATOR);
    }

    private TournamentDTO buildTournament() {
        TournamentDTO dto = new TournamentDTO();
        dto.setDirectorId("TEST_12345");
        return dto;
    }

    private TournamentCollaboratorDTO buildCollaborator(boolean adminAccess) {
        TournamentCollaboratorDTO dto = new TournamentCollaboratorDTO();
        dto.setTournamentId("SDAD");
        dto.setUserId("USER");
        dto.setIsAdmin(adminAccess);

        return dto;
    }
}
