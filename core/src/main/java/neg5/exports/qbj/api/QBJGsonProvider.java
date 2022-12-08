package neg5.exports.qbj.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Singleton;
import java.util.function.Supplier;
import neg5.gson.IdentifiableTypeAdapterFactory;

@Singleton
public class QBJGsonProvider implements Supplier<Gson> {

    private static final Gson GSON =
            new GsonBuilder()
                    .registerTypeAdapterFactory(new IdentifiableTypeAdapterFactory())
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

    @Override
    public Gson get() {
        return GSON;
    }
}
