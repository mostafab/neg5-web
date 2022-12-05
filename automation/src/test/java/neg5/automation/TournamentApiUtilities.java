package neg5.automation;

import static io.restassured.RestAssured.given;
import static neg5.automation.ApiParsingUtilities.parseBody;
import static neg5.automation.ApiParsingUtilities.toJsonString;

import io.restassured.response.Response;
import neg5.domain.api.TournamentDTO;

public class TournamentApiUtilities {

    public static TournamentDTO createStubTournament(User user) {
        TournamentDTO basic = new TournamentDTO();
        basic.setName("Test Tournament");
        basic.setBonusPointValue(10L);
        basic.setPartsPerBonus(3L);
        basic.setMaxActivePlayersPerTeam(4);

        return createTournament(user, basic);
    }

    public static TournamentDTO createTournament(User user, TournamentDTO tournament) {
        Response response =
                given().cookie("nfToken", user.getNfToken())
                        .body(toJsonString(tournament))
                        .when()
                        .post("/neg5-api/tournaments");
        response.then().statusCode(200);

        return parseBody(response, TournamentDTO.class);
    }
}
