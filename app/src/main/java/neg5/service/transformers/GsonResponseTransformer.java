package neg5.service.transformers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.gson.GsonProvider;
import spark.ResponseTransformer;

@Singleton
public class GsonResponseTransformer implements ResponseTransformer {

    @Inject private GsonProvider gsonProvider;

    @Override
    public String render(Object o) {
        return getGson().toJson(o);
    }

    private Gson getGson() {
        return gsonProvider.get();
    }
}
