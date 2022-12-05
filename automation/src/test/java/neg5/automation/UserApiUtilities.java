package neg5.automation;

import static io.restassured.RestAssured.given;
import static neg5.automation.ApiParsingUtilities.toJsonString;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import io.restassured.response.Response;
import neg5.domain.api.AccountCreationDTO;

public class UserApiUtilities {

    public static User createUserAndLogin() {
        AccountCreationDTO account = new AccountCreationDTO();
        String randomString = NanoIdUtils.randomNanoId();
        account.setEmail(String.format("first.last.%s@domain.com", randomString));
        account.setName("First Last");
        account.setPassword(randomString);
        account.setUsername(String.format("first-last-%s", randomString));

        Response response = given().body(toJsonString(account)).when().post("/neg5-api/accounts");

        response.then().statusCode(200).cookie("nfToken");

        return new User(response.cookie("nfToken"), account.getUsername());
    }
}
