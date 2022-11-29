package neg5.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.neg5.enums.StringIdentifiable;

/**
 * Type adapter factory for enums that implement {@link org.neg5.enums.Identifiable}
 */
public class IdentifiableTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        boolean isStringIdentifiable = StringIdentifiable.class.isAssignableFrom(rawType);

        if (isStringIdentifiable) {
            return (TypeAdapter<T>) new StringIdentifiableTypeAdapterProvider().get((TypeToken<StringIdentifiable>) type);
        } else {
            return null;
        }
    }
}
