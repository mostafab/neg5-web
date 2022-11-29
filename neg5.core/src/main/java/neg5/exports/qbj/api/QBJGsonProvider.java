package neg5.exports.qbj.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provider;
import neg5.gson.IdentifiableTypeAdapterFactory;

public class QBJGsonProvider implements Provider<Gson> {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new IdentifiableTypeAdapterFactory())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Override
    public Gson get() {
        return gson;
    }
}
