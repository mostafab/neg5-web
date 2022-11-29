package org.neg5.filters;

import com.google.inject.Inject;
import neg5.gson.GsonProvider;
import neg5.validation.ObjectValidationException;

import static spark.Spark.exception;

public class ObjectValidationExceptionHandlerFilter implements RequestFilter {

    private final GsonProvider gsonProvider;

    @Inject
    public ObjectValidationExceptionHandlerFilter(GsonProvider gsonProvider) {
        this.gsonProvider = gsonProvider;
    }

    @Override
    public void registerFilter() {
        exception(ObjectValidationException.class, (exception, request, response) -> {
            response.status(400);
            response.body(gsonProvider.get().toJson(exception.getErrors()));
        });
    }
}
