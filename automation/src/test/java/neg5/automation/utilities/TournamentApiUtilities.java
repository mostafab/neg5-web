package neg5.automation.utilities;

import static neg5.automation.utilities.ApiParsingUtilities.doRequestAndParse;
import static neg5.automation.utilities.ApiParsingUtilities.parseBody;
import static neg5.automation.utilities.ApiParsingUtilities.toJsonString;
import static neg5.automation.utilities.DataUtilities.faker;
import static neg5.automation.utilities.UserApiUtilities.givenAsUser;

import io.restassured.response.Response;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamDTO;

public class TournamentApiUtilities {

    public static TournamentDTO createStubTournament(User user) {
        TournamentDTO basic = new TournamentDTO();
        basic.setName(faker().superhero().descriptor());
        basic.setBonusPointValue(10L);
        basic.setPartsPerBonus(3L);
        basic.setMaxActivePlayersPerTeam(4);

        return createTournament(user, basic);
    }

    public static TournamentDTO createTournament(User user, TournamentDTO tournament) {
        Response response =
                givenAsUser(user)
                        .body(toJsonString(tournament))
                        .when()
                        .post("/neg5-api/tournaments");
        response.then().statusCode(200);

        return parseBody(response, TournamentDTO.class);
    }

    public static TournamentTeamDTO createTeam(User user, TournamentTeamDTO team) {
        return doRequestAndParse(
                TournamentTeamDTO.class,
                () -> givenAsUser(user).body(toJsonString(team)).post("/neg5-api/teams"));
    }

    public static TournamentPlayerDTO createPlayer(User user, TournamentPlayerDTO player) {
        return doRequestAndParse(
                TournamentPlayerDTO.class,
                () -> givenAsUser(user).body(toJsonString(player)).post("/neg5-api/players"));
    }

    public static TournamentMatchDTO createMatch(User user, TournamentMatchDTO match) {
        return doRequestAndParse(
                TournamentMatchDTO.class,
                () -> givenAsUser(user).body(toJsonString(match)).post("/neg5-api/matches"));
    }
}
