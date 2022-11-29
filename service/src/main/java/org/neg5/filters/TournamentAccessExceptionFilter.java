package org.neg5.filters;

import com.google.inject.Inject;
import org.neg5.ClientExceptionDTO;
import neg5.gson.GsonProvider;
import neg5.accessManager.api.TournamentAccessException;

import static spark.Spark.exception;

/**
 * Web request exception handler for {@link TournamentAccessException}
 */
public class TournamentAccessExceptionFilter implements RequestFilter {

    @Inject
    private GsonProvider gsonProvider;

    @Override
    public void registerFilter() {
        exception(TournamentAccessException.class, (ex, request, response) -> {
            response.status(403);
            ClientExceptionDTO clientException = new ClientExceptionDTO();
            clientException.setMessage(ex.getMessage());

            response.body(gsonProvider.get().toJson(clientException));
        });
    }
}
