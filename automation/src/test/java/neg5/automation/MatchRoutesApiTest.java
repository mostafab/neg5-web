package neg5.automation;

import static io.restassured.RestAssured.given;
import static neg5.automation.utilities.ApiParsingUtilities.doRequestAndParse;
import static neg5.automation.utilities.ApiParsingUtilities.parseBody;
import static neg5.automation.utilities.ApiParsingUtilities.toJsonString;
import static neg5.automation.utilities.DataUtilities.faker;
import static neg5.automation.utilities.TournamentApiUtilities.createMatch;
import static neg5.automation.utilities.TournamentApiUtilities.createStubTournament;
import static neg5.automation.utilities.TournamentApiUtilities.createTeam;
import static neg5.automation.utilities.UserApiUtilities.createUser;
import static neg5.automation.utilities.UserApiUtilities.givenAsUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.Sets;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import neg5.automation.utilities.User;
import neg5.domain.api.MatchPlayerAnswerDTO;
import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchRoutesApiTest extends BaseRoutesApiTest {

    private User user;
    private TournamentDTO tournament;
    private MatchTeamDTO firstTeam;
    private MatchTeamDTO secondTeam;

    private TournamentMatchDTO match;
    private String phaseId;

    @BeforeEach
    public void setup() {
        user = createUser();
        tournament = createStubTournament(user);

        firstTeam = createMatchTeam(200, 7);
        secondTeam = createMatchTeam(210, 7);

        match = new TournamentMatchDTO();
        match.setRound(1L);
        match.setTossupsHeard(20);
        match.setTournamentId(tournament.getId());
        match.setRoom(faker().space().planet());
        match.setModerator(faker().dragonBall().character());
        match.setPacket(faker().book().title());
        match.setNotes(faker().gameOfThrones().quote());

        phaseId = tournament.getPhases().stream().findFirst().get().getId();
        match.setPhases(Sets.newHashSet(phaseId));

        match.setTeams(Sets.newHashSet(firstTeam, secondTeam));
    }

    @Test
    public void testCreateMatchBasicTeamData() {
        firstTeam.setPlayers(null);
        secondTeam.setPlayers(null);

        TournamentMatchDTO created = createMatch(user, match);

        assertNotNull(created.getId());
        assertNotNull(created.getAddedAt());
        assertEquals(match.getRound(), created.getRound());
        assertEquals(match.getTossupsHeard(), created.getTossupsHeard());
        assertEquals(tournament.getId(), created.getTournamentId());
        assertEquals(phaseId, created.getPhases().stream().findFirst().get());
        assertEquals(match.getTeams().size(), created.getTeams().size());

        assertEquals(match.getRoom(), created.getRoom());
        assertEquals(match.getModerator(), created.getModerator());
        assertEquals(match.getPacket(), created.getPacket());
        assertEquals(match.getNotes(), created.getNotes());

        created.getTeams().forEach(team -> assertTrue(team.getPlayers().isEmpty()));
    }

    @Test
    public void testCreateMatchWithPlayerData() {
        TournamentMatchDTO created = createMatch(user, match);
        assertEquals(match.getTeams().size(), created.getTeams().size());

        created.getTeams()
                .forEach(
                        team -> {
                            assertEquals(1, team.getPlayers().size());
                            assertNotNull(team.getTeamId());
                            assertEquals(created.getId(), team.getMatchId());
                            assertEquals(tournament.getId(), team.getTournamentId());

                            team.getPlayers()
                                    .forEach(
                                            player -> {
                                                assertNotNull(player.getPlayerId());
                                                assertNotNull(player.getMatchId());
                                                assertNotNull(player.getTossupsHeard());
                                                assertNotNull(player.getTournamentId());
                                                assertEquals(1, player.getAnswers().size());
                                            });
                        });
    }

    @Test
    public void testCreateMatchAndFetchAll() {
        TournamentMatchDTO created = createMatch(user, match);
        List<TournamentMatchDTO> matches =
                parseBody(
                        given().get("/neg5-api/tournaments/" + tournament.getId() + "/matches"),
                        new TypeToken<List<TournamentMatchDTO>>() {});

        assertEquals(1, matches.size());
        assertEquals(created.getId(), matches.get(0).getId());
    }

    @Test
    public void testUpdateMatch() {
        TournamentMatchDTO created = createMatch(user, match);
        created.setRound(2L);

        TournamentMatchDTO updated =
                doRequestAndParse(
                        TournamentMatchDTO.class,
                        () -> givenAsUser(user)
                                .body(toJsonString(created))
                                .put("neg5-api/matches/" + created.getId()));
        // "Updating" a match actually creates a new one
        assertNotEquals(created.getId(), updated.getId());
        assertNotNull(updated.getId());
        assertEquals(created.getTournamentId(), updated.getTournamentId());
    }

    private MatchTeamDTO createMatchTeam(int score, int tossupsGotten) {
        TournamentTeamDTO team = new TournamentTeamDTO();
        team.setTournamentId(tournament.getId());
        team.setName(faker().university().name());
        team.setPlayers(new HashSet<>());

        TournamentPlayerDTO player = new TournamentPlayerDTO();
        player.setName(faker().artist().name());

        team.getPlayers().add(player);

        TournamentTeamDTO created = createTeam(user, team);

        MatchTeamDTO matchTeam = new MatchTeamDTO();
        matchTeam.setTeamId(created.getId());
        matchTeam.setScore(score);
        matchTeam.setPlayers(new ArrayList<>());

        String playerId = created.getPlayers().stream().findFirst().get().getId();

        MatchPlayerDTO matchPlayer = new MatchPlayerDTO();
        matchPlayer.setPlayerId(playerId);
        matchPlayer.setTossupsHeard(20);
        matchPlayer.setAnswers(new HashSet<>());

        matchTeam.getPlayers().add(matchPlayer);

        MatchPlayerAnswerDTO answers = new MatchPlayerAnswerDTO();
        answers.setTossupValue(10);
        answers.setNumberGotten(tossupsGotten);

        matchPlayer.getAnswers().add(answers);

        return matchTeam;
    }
}
