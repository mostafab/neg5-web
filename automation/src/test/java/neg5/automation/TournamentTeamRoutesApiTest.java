package neg5.automation;

import static io.restassured.RestAssured.given;
import static neg5.automation.utilities.ApiParsingUtilities.parseBody;
import static neg5.automation.utilities.ApiParsingUtilities.toJsonString;
import static neg5.automation.utilities.TournamentApiUtilities.createStubTournament;
import static neg5.automation.utilities.TournamentApiUtilities.createTeam;
import static neg5.automation.utilities.UserApiUtilities.createUser;
import static neg5.automation.utilities.UserApiUtilities.givenAsUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import neg5.automation.utilities.User;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TournamentTeamRoutesApiTest extends BaseRoutesApiTest {

    private User user;
    private String tournamentId;
    private TournamentTeamDTO team;

    @BeforeEach
    public void setup() {
        user = createUser();
        tournamentId = createStubTournament(user).getId();

        team = new TournamentTeamDTO();
        team.setTournamentId(tournamentId);
        team.setName("Norcross A");
        team.setPlayers(new HashSet<>());
    }

    @Test
    public void testCreateTeam() {
        TournamentTeamDTO created = createTeam(user, team);

        assertEquals(tournamentId, created.getTournamentId());
        assertEquals(team.getName(), created.getName());
        assertNotNull(created.getId());
        assertNotNull(created.getDivisions());
        assertTrue(created.getDivisions().isEmpty());
        assertNotNull(created.getPlayers());
        assertTrue(created.getPlayers().isEmpty());
    }

    @Test
    public void testCreateTeamWithPlayers() {
        TournamentPlayerDTO player = new TournamentPlayerDTO();
        player.setName("Hernan Morales");

        team.getPlayers().add(player);

        TournamentTeamDTO created = createTeam(user, team);
        assertEquals(1, created.getPlayers().size());

        TournamentPlayerDTO createdPlayer = created.getPlayers().stream().findFirst().get();
        assertEquals(created.getId(), createdPlayer.getTeamId());
        assertNotNull(createdPlayer.getId());
        assertEquals(tournamentId, createdPlayer.getTournamentId());
        assertEquals(player.getName(), createdPlayer.getName());
    }

    @Test
    public void testUpdateTeam() {
        team.setPlayers(new HashSet<>());
        TournamentPlayerDTO player = new TournamentPlayerDTO();
        player.setName("Hernan Morales");
        team.getPlayers().add(player);

        TournamentTeamDTO created = createTeam(user, team);
        created.setName(created.getName() + " - Modified");
        TournamentTeamDTO updated =
                parseBody(
                        givenAsUser(user)
                                .body(toJsonString(created))
                                .put("/neg5-api/teams/" + created.getId()),
                        TournamentTeamDTO.class);

        assertEquals(created.getName(), updated.getName());
        assertEquals(created.getId(), updated.getId());
        assertEquals(tournamentId, updated.getTournamentId());
    }

    @Test
    public void testDeleteTeam() {
        team.setPlayers(new HashSet<>());
        TournamentPlayerDTO player = new TournamentPlayerDTO();
        player.setName("Hernan Morales");
        team.getPlayers().add(player);

        TournamentTeamDTO created = createTeam(user, team);

        givenAsUser(user).delete("/neg5-api/teams/" + created.getId()).then().statusCode(204);
        givenAsUser(user).get("/neg5-api/teams/" + created.getId()).then().statusCode(404);
    }

    @Test
    public void testCreateAndUpdateTeamUnauthorizedUser() {
        given().body(toJsonString(team)).post("/neg5-api/teams").then().statusCode(403);

        User unauthorizedUser = createUser();
        givenAsUser(unauthorizedUser)
                .body(toJsonString(team))
                .post("/neg5-api/teams")
                .then()
                .statusCode(403);

        TournamentTeamDTO created = createTeam(user, team);
        givenAsUser(unauthorizedUser)
                .body(toJsonString(created))
                .put("/neg5-api/teams/" + created.getId())
                .then()
                .statusCode(403);
    }
}
