package neg5.service.exceptionHandlers;

import static spark.Spark.exception;

import com.google.inject.Inject;
import neg5.gson.GsonProvider;
import neg5.validation.ObjectValidationException;

public class ObjectValidationExceptionHandler implements ExceptionHandler {

    private final GsonProvider gsonProvider;

    @Inject
    public ObjectValidationExceptionHandler(GsonProvider gsonProvider) {
        this.gsonProvider = gsonProvider;
    }

    @Override
    public void registerHandler() {
        exception(
                ObjectValidationException.class,
                (exception, request, response) -> {
                    response.status(400);
                    response.body(gsonProvider.get().toJson(exception.getErrors()));
                });
    }
}
