package neg5.service.filters;

import static spark.Spark.exception;

import com.google.inject.Inject;
import javax.persistence.NoResultException;
import neg5.domain.api.ClientExceptionDTO;
import neg5.gson.GsonProvider;

public class NoResultHandlerFilter implements RequestFilter {

    @Inject private GsonProvider gsonProvider;

    @Override
    public void registerFilter() {
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
