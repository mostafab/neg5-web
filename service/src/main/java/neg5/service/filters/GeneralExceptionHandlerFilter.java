package neg5.service.filters;

import static spark.Spark.exception;

import com.google.inject.Inject;
import neg5.domain.api.ClientExceptionDTO;
import neg5.gson.GsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralExceptionHandlerFilter implements RequestFilter {

    @Inject private GsonProvider gsonProvider;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GeneralExceptionHandlerFilter.class);

    @Override
    public void registerFilter() {
        exception(
                Exception.class,
                (exception, request, response) -> {
                    LOGGER.error(exception.getMessage(), exception);
                    response.status(500);
                    ClientExceptionDTO clientException = new ClientExceptionDTO();
                    clientException.setMessage("There was an internal server error.");
                    response.body(gsonProvider.get().toJson(clientException));
                });
    }
}
