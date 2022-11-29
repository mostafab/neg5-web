package neg5.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.neg5.enums.Identifiable;

/**
 * Interface for providers of a type adapter
 * @param <T>
 */
public interface IdentifiableTypeAdapterProvider<T extends Identifiable> {

    TypeAdapter<T> get(TypeToken<T> typeToken);
}
