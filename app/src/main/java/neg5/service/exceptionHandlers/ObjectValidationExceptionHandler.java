package neg5.service.exceptionHandlers;

import static spark.Spark.exception;

import com.google.inject.Inject;
import neg5.gson.GsonProvider;
import neg5.validation.ObjectValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectValidationExceptionHandler implements ExceptionHandler {

    private final GsonProvider gsonProvider;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ObjectValidationExceptionHandler.class);

    @Inject
    public ObjectValidationExceptionHandler(GsonProvider gsonProvider) {
        this.gsonProvider = gsonProvider;
    }

    @Override
    public void registerHandler() {
        exception(
                ObjectValidationException.class,
                (exception, request, response) -> {
                    LOGGER.warn(
                            String.format(
                                    "Validation failed for in endpoint, method=%s uri=%s",
                                    request.requestMethod(), request.uri()),
                            exception);
                    response.status(400);
                    response.body(gsonProvider.get().toJson(exception.getErrors()));
                });
    }
}
