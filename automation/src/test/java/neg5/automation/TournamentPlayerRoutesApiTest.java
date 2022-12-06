package neg5.automation;

import static io.restassured.RestAssured.given;
import static neg5.automation.utilities.ApiParsingUtilities.parseBody;
import static neg5.automation.utilities.ApiParsingUtilities.toJsonString;
import static neg5.automation.utilities.DataUtilities.faker;
import static neg5.automation.utilities.TournamentApiUtilities.createPlayer;
import static neg5.automation.utilities.TournamentApiUtilities.createStubTournament;
import static neg5.automation.utilities.TournamentApiUtilities.createTeam;
import static neg5.automation.utilities.UserApiUtilities.createUser;
import static neg5.automation.utilities.UserApiUtilities.givenAsUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import neg5.automation.utilities.User;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TournamentPlayerRoutesApiTest extends BaseRoutesApiTest {

    private User user;
    private String tournamentId;
    private String teamId;

    TournamentPlayerDTO player;

    @BeforeEach
    public void setup() {
        user = createUser();
        tournamentId = createStubTournament(user).getId();

        TournamentTeamDTO team = new TournamentTeamDTO();
        team = new TournamentTeamDTO();
        team.setTournamentId(tournamentId);
        team.setName("Norcross A");
        team.setPlayers(new HashSet<>());
        teamId = createTeam(user, team).getId();

        player = new TournamentPlayerDTO();
        player.setName(faker().aquaTeenHungerForce().character());
        player.setTeamId(teamId);
        player.setTournamentId(tournamentId);
    }

    @Test
    public void testCreatePlayer() {
        TournamentPlayerDTO created = createPlayer(user, player);
        assertNotNull(created.getId());
        assertEquals(teamId, created.getTeamId());
        assertEquals(tournamentId, created.getTournamentId());
        assertEquals(player.getName(), created.getName());
    }

    @Test
    public void testCreatePlayerInvalidInput() {
        player.setName(null);
        givenAsUser(user)
                .body(toJsonString(player))
                .post("/neg5-api/players")
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdatePlayer() {
        TournamentPlayerDTO created = createPlayer(user, player);
        created.setName(created.getName() + " - Modified");
        created.setTeamId("Invalid team id");
        created.setTournamentId("Invalid tournament id");
        TournamentPlayerDTO updated =
                parseBody(
                        givenAsUser(user)
                                .body(toJsonString(created))
                                .put("neg5-api/players/" + created.getId()),
                        TournamentPlayerDTO.class);
        assertEquals(created.getId(), updated.getId());
        assertEquals(created.getName(), updated.getName());
        assertEquals(teamId, updated.getTeamId());
        assertEquals(tournamentId, updated.getTournamentId());
    }

    @Test
    public void testDeletePlayer() {
        TournamentPlayerDTO created = createPlayer(user, player);
        givenAsUser(user)
                .body(toJsonString(player))
                .delete("/neg5-api/players/" + created.getId())
                .then()
                .statusCode(204);
    }

    @Test
    public void testCreateOrUpdatePlayerUnauthorizedUser() {
        given().body(toJsonString(player)).post("/neg5-api/players").then().statusCode(403);

        User unauthorizedUser = createUser();
        givenAsUser(unauthorizedUser)
                .body(toJsonString(player))
                .post("/neg5-api/players")
                .then()
                .statusCode(403);

        TournamentPlayerDTO created = createPlayer(user, player);
        givenAsUser(unauthorizedUser)
                .body(toJsonString(created))
                .put("/neg5-api/players/" + created.getId())
                .then()
                .statusCode(403);
    }
}
