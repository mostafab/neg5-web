package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.service.transformers.GsonResponseTransformer;
import spark.Route;

/** Controller that handles converting responses to JSON payloads */
public abstract class AbstractJsonController extends AbstractController {

    @Inject private GsonResponseTransformer gsonResponseTransformer;

    @Override
    protected GsonResponseTransformer getResponseTransformer() {
        return gsonResponseTransformer;
    }

    @Override
    protected Route getRequestHandler(Route handler) {
        return (request, response) -> {
            response.type("application/json");
            return handler.handle(request, response);
        };
    }
}
