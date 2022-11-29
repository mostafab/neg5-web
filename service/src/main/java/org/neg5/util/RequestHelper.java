package org.neg5.util;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.gson.GsonProvider;
import spark.Request;

import java.lang.reflect.Type;

@Singleton
public class RequestHelper {

    @Inject private GsonProvider gsonProvider;

    public <T> T readFromRequest(Request request, Type type) {
        return gsonProvider.get().fromJson(request.body(), type);
    }

    public <T> T readFromRequest(Request request, Class<T> bodyClazz) {
        return gsonProvider.get().fromJson(request.body(), bodyClazz);
    }
}
