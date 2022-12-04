package neg5.service.exceptionHandlers;

import static spark.Spark.exception;

import com.google.inject.Inject;
import neg5.domain.api.ClientExceptionDTO;
import neg5.gson.GsonProvider;
import neg5.monitoring.api.ExceptionAlerter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralExceptionHandler implements ExceptionHandler {

    @Inject private GsonProvider gsonProvider;
    @Inject private ExceptionAlerter exceptionAlerter;

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @Override
    public void registerHandler() {
        exception(
                Exception.class,
                (exception, request, response) -> {
                    LOGGER.error(exception.getMessage(), exception);
                    exceptionAlerter.notifyOfException(exception);

                    response.status(500);
                    ClientExceptionDTO clientException = new ClientExceptionDTO();
                    clientException.setMessage("There was an internal server error.");
                    response.body(gsonProvider.get().toJson(clientException));
                });
    }
}
