package neg5.service.transformers;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JsonTransformerProvider implements Provider<JsonTransformer> {

    @Inject private JsonTransformer jsonTransformer;

    @Override
    public JsonTransformer get() {
        return jsonTransformer;
    }
}
