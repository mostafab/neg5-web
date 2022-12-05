package neg5.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import neg5.domain.api.enums.StringIdentifiable;

/**
 * Implementation of {@link IdentifiableTypeAdapterProvider} for {@link StringIdentifiable} enums
 */
public class StringIdentifiableTypeAdapterProvider
        implements IdentifiableTypeAdapterProvider<StringIdentifiable> {

    @Override
    public TypeAdapter<StringIdentifiable> get(TypeToken<StringIdentifiable> type) {
        Class<StringIdentifiable> rawType = (Class<StringIdentifiable>) type.getRawType();
        return new TypeAdapter<StringIdentifiable>() {

            @Override
            public StringIdentifiable read(JsonReader in) throws IOException {
                JsonToken token = in.peek();
                if (JsonToken.NULL == token) {
                    in.nextNull();
                    return null;
                }
                String id = in.nextString();
                StringIdentifiable[] constants =
                        type.getRawType().asSubclass(StringIdentifiable.class).getEnumConstants();
                for (StringIdentifiable value : constants) {
                    if (id.equals(value.getId())) {
                        return rawType.cast(value);
                    }
                }
                throw new IOException(
                        "Unsupported enum value: " + id + ", for enum " + rawType.getName());
            }

            @Override
            public void write(JsonWriter out, StringIdentifiable value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.getId());
                }
            }
        };
    }
}
