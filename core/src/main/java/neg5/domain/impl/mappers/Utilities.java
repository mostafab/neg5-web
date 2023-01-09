package neg5.domain.impl.mappers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import neg5.gson.GsonProvider;

class Utilities {

    private static final Gson GSON = new GsonProvider().get();
    private static final Type STRING_LIST_TYPE = new TypeToken<List<String>>() {}.getType();

    static List<String> mapFromCommaSeparatedString(String input) {
        if (input == null) {
            return new ArrayList<>();
        }
        return GSON.fromJson(input, STRING_LIST_TYPE);
    }
}
