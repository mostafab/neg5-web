package neg5.automation;

import static io.restassured.RestAssured.given;
import static neg5.automation.utilities.ApiParsingUtilities.doRequestAndParse;
import static neg5.automation.utilities.ApiParsingUtilities.toJsonString;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import neg5.domain.api.AccountCreationDTO;
import neg5.domain.api.AccountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountRoutesApiTest extends BaseRoutesApiTest {

    private AccountCreationDTO account;
    private AccountDTO result;

    @BeforeEach
    public void createAccount() {
        account = new AccountCreationDTO();
        String randomString = NanoIdUtils.randomNanoId();
        account.setEmail(String.format("first.last.%s@domain.com", randomString));
        account.setName("First Last");
        account.setPassword(randomString);
        account.setUsername(String.format("first-last-%s", randomString));

        result =
                doRequestAndParse(
                        AccountDTO.class,
                        () ->
                                given().body(toJsonString(account))
                                        .when()
                                        .post("/neg5-api/accounts"));
    }

    @Test
    public void testCreateAccount() {
        assertEquals(account.getEmail(), result.getEmail());
        assertEquals(account.getUsername(), result.getId());
        assertEquals(account.getName(), result.getName());
    }

    @Test
    public void testLoginValidCredentials() {
        String body =
                String.format(
                        "{\"username\":\"%s\", \"password\":\"%s\"}",
                        account.getUsername(), account.getPassword());
        given().body(body)
                .when()
                .post("/neg5-api/login")
                .then()
                .statusCode(200)
                .header("NEG5_TOKEN", any(String.class));
    }

    @Test
    public void testLoginInValidCredentials() {
        String body =
                String.format(
                        "{\"username\":\"%s\", \"password\":\"%s\"}",
                        account.getUsername(), account.getPassword() + "garbage");
        given().body(body).when().post("/neg5-api/login").then().statusCode(403);
    }
}
