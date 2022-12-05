package neg5.automation;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost:1337";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    public RequestSpecification asUser(User user) {
        return given().cookie("nfToken", user.getNfToken());
    }
}
