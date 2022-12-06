package neg5.automation;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public class BaseRoutesApiTest {

    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost:1337";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
