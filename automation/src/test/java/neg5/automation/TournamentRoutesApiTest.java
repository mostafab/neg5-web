package neg5.automation;

import static neg5.automation.utilities.ApiParsingUtilities.doRequestAndParse;
import static neg5.automation.utilities.ApiParsingUtilities.parseBody;
import static neg5.automation.utilities.ApiParsingUtilities.toJsonString;
import static neg5.automation.utilities.UserApiUtilities.createUser;
import static neg5.automation.utilities.UserApiUtilities.givenAsUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import neg5.automation.utilities.TournamentApiUtilities;
import neg5.automation.utilities.User;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.UpdateTournamentRequestDTO;
import neg5.domain.api.UserTournamentsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TournamentRoutesApiTest extends BaseRoutesApiTest {

    private User user;
    private TournamentDTO tournament;

    @BeforeEach
    public void setup() {
        user = createUser();
        tournament = TournamentApiUtilities.createStubTournament(user);
    }

    @Test
    public void testCreateTournament() {
        String tournamentId = tournament.getId();

        assertNotNull(tournamentId);
        assertEquals(user.getUsername(), tournament.getDirectorId());
        assertNotNull(tournament.getName());
        assertNotNull(tournament.getPartsPerBonus());
        assertNotNull(tournament.getBonusPointValue());
        assertNotNull(tournament.getMaxActivePlayersPerTeam());
        assertTrue(tournament.getPhases() != null && tournament.getPhases().size() == 1);

        tournament
                .getTossupValues()
                .forEach(
                        tv -> {
                            assertEquals(tournamentId, tv.getTournamentId());
                            assertNotNull(tv.getValue());
                            assertNotNull(tv.getAnswerType());
                        });
    }

    @Test
    public void testCreateAndFetch() {
        String tournamentId = tournament.getId();
        TournamentDTO result =
                doRequestAndParse(
                        TournamentDTO.class,
                        () -> givenAsUser(user).get("/neg5-api/tournaments/" + tournamentId));

        assertEquals(tournamentId, result.getId());
    }

    @Test
    public void testUpdateTournament() {
        UpdateTournamentRequestDTO updateRequest = new UpdateTournamentRequestDTO();
        updateRequest.setLocation("Home");
        updateRequest.setTournamentDate(LocalDate.of(2022, 1, 1));
        updateRequest.setComments("Some Comments");
        updateRequest.setName("New Test Name");
        updateRequest.setQuestionSet("Question Set");

        TournamentDTO updated =
                doRequestAndParse(
                        TournamentDTO.class,
                        () ->
                                givenAsUser(user)
                                        .body(toJsonString(updateRequest))
                                        .when()
                                        .put("/neg5-api/tournaments/" + tournament.getId()));
        assertEquals(tournament.getId(), updated.getId());
        assertEquals(updateRequest.getLocation(), updated.getLocation());
        assertEquals(updateRequest.getName(), updated.getName());
        assertEquals(updateRequest.getTournamentDate(), updated.getTournamentDate());
        assertEquals(updateRequest.getComments(), updated.getComments());
        assertEquals(updateRequest.getQuestionSet(), updated.getQuestionSet());
    }

    @Test
    public void testUpdateTournamentUnauthorizedUser() {
        UpdateTournamentRequestDTO updateRequest = new UpdateTournamentRequestDTO();
        updateRequest.setLocation("Home");
        updateRequest.setTournamentDate(LocalDate.of(2022, 1, 1));
        updateRequest.setComments("Some Comments");
        updateRequest.setName("New Test Name");
        updateRequest.setQuestionSet("Question Set");

        givenAsUser(createUser())
                .body(toJsonString(updateRequest))
                .when()
                .put("/neg5-api/tournaments/" + tournament.getId())
                .then()
                .statusCode(403);
    }

    @Test
    public void testGetUserTournamentsLoggedIn() {
        UserTournamentsDTO tournaments =
                parseBody(givenAsUser(user).get("/neg5-api/tournaments"), UserTournamentsDTO.class);
        assertEquals(1, tournaments.getUserOwnedTournaments().size());
        assertTrue(tournaments.getCollaboratingTournaments().isEmpty());
    }
}
