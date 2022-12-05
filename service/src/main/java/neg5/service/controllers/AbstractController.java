package neg5.service.controllers;

import com.newrelic.api.agent.NewRelic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ResponseTransformer;
import spark.Route;
import spark.Spark;

public abstract class AbstractController implements BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected abstract String getBasePath();

    protected void get(String path, Route route) {
        get(path, route, getResponseTransformer());
    }

    protected void get(String path, Route route, ResponseTransformer responseTransformer) {
        String fullPath = constructPath(path);
        if (responseTransformer == null) {
            Spark.get(fullPath, internalEnrichRoute(route, fullPath));
        } else {
            Spark.get(fullPath, internalEnrichRoute(route, fullPath), responseTransformer);
        }
        LOGGER.info("Registered GET route {}", fullPath);
    }

    protected void post(String path, Route route) {
        post(path, route, getResponseTransformer());
    }

    protected void post(String path, Route route, ResponseTransformer responseTransformer) {
        String fullPath = constructPath(path);
        if (responseTransformer == null) {
            Spark.post(fullPath, internalEnrichRoute(route, fullPath));
        } else {
            Spark.post(fullPath, internalEnrichRoute(route, fullPath), responseTransformer);
        }
        LOGGER.info("Registered POST route {}", fullPath);
    }

    protected void put(String path, Route route) {
        put(path, route, getResponseTransformer());
    }

    protected void put(String path, Route route, ResponseTransformer responseTransformer) {
        String fullPath = constructPath(path);
        if (responseTransformer == null) {
            Spark.put(fullPath, internalEnrichRoute(route, fullPath));
        } else {
            Spark.put(fullPath, internalEnrichRoute(route, fullPath), responseTransformer);
        }
        LOGGER.info("Registered PUT route {}", fullPath);
    }

    protected void delete(String path, Route route) {
        String fullPath = constructPath(path);
        Spark.delete(fullPath, internalEnrichRoute(route, fullPath));
        LOGGER.info("Registered DELETE route {}", fullPath);
    }

    protected Route enrichRoute(Route route) {
        return route;
    }

    protected ResponseTransformer getResponseTransformer() {
        return null;
    }

    private Route internalEnrichRoute(Route handler, String fullEndpointRoutePattern) {
        return (request, response) -> {
            NewRelic.setTransactionName(null, fullEndpointRoutePattern);
            return handler.handle(request, response);
        };
    }

    private String constructPath(String path) {
        return getBasePath() + path;
    }
}
