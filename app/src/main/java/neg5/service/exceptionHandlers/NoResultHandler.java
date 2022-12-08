package neg5.service.exceptionHandlers;

import static spark.Spark.exception;

import com.google.inject.Inject;
import javax.persistence.NoResultException;
import neg5.domain.api.ClientExceptionDTO;
import neg5.gson.GsonProvider;

public class NoResultHandler implements ExceptionHandler {

    @Inject private GsonProvider gsonProvider;

    @Override
    public void registerHandler() {
        exception(
                NoResultException.class,
                (exception, request, response) -> {
                    response.status(404);

                    ClientExceptionDTO clientException = new ClientExceptionDTO();
                    clientException.setMessage(exception.getMessage());

                    response.body(gsonProvider.get().toJson(clientException));
                });
    }
}
