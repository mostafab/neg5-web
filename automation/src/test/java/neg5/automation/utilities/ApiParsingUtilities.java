package neg5.automation.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    public static <T> T parseBody(String json, TypeToken<T> typeToken) {
        return GSON.fromJson(json, typeToken.getType());
    }

    public static <T> T parseBody(Response response, Class<T> clazz) {
        return parseBody(response.asString(), clazz);
    }

    public static <T> T parseBody(Response response, TypeToken<T> typeToken) {
        return parseBody(response.asString(), typeToken);
    }

    public static <T> T doRequestAndParse(Class<T> clazz, Supplier<Response> responseSupplier) {
        Response response = responseSupplier.get();
        response.then().statusCode(200);
        return parseBody(response, clazz);
    }

    public static <T> T doRequestAndParse(
            TypeToken<T> typeToken, Supplier<Response> responseSupplier) {
        Response response = responseSupplier.get();
        response.then().statusCode(200);
        return parseBody(response, typeToken);
    }
}
