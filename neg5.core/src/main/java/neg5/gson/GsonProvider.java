package neg5.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.time.Instant;
import java.time.LocalDate;

@Singleton
public class GsonProvider implements Provider<Gson> {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new IdentifiableTypeAdapterFactory())
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
            .create();

    @Override
    public Gson get() {
        return gson;
    }
}
