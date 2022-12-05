package neg5.automation;

import static neg5.automation.UserApiUtilities.createUserAndLogin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import neg5.domain.api.TournamentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TournamentApiTest extends BaseApiTest {

    private User user;
    private TournamentDTO tournament;

    @BeforeEach
    public void setup() {
        user = createUserAndLogin();
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
}
