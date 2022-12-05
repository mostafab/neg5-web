package neg5.automation;

import com.google.gson.Gson;
import io.restassured.response.Response;
import java.util.function.Supplier;
import neg5.gson.GsonProvider;

public class ApiParsingUtilities {

    private static final Gson GSON = new GsonProvider().get();

    public static String toJsonString(Object o) {
        return GSON.toJson(o);
    }

    public static <T> T parseBody(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static <T> T parseBody(Response response, Class<T> clazz) {
        return parseBody(response.asString(), clazz);
    }

    public static <T> T doRequestAndParse(Class<T> clazz, Supplier<Response> responseSupplier) {
        Response response = responseSupplier.get();
        response.then().statusCode(200);
        return parseBody(response, clazz);
    }
}
