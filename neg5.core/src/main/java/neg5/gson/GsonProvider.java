package neg5.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Singleton;
import java.time.Instant;
import java.time.LocalDate;
import java.util.function.Supplier;

@Singleton
public class GsonProvider implements Supplier<Gson> {

    private static final Gson GSON =
            new GsonBuilder()
                    .registerTypeAdapterFactory(new IdentifiableTypeAdapterFactory())
                    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                    .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                    .create();

    @Override
    public Gson get() {
        return GSON;
    }
}
