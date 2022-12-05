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
            Spark.get(fullPath, internalRequestHandler(route, fullPath));
        } else {
            Spark.get(fullPath, internalRequestHandler(route, fullPath), responseTransformer);
        }
        LOGGER.info("Registered GET route {}", fullPath);
    }

    protected void post(String path, Route route) {
        post(path, route, getResponseTransformer());
    }

    protected void post(String path, Route route, ResponseTransformer responseTransformer) {
        String fullPath = constructPath(path);
        if (responseTransformer == null) {
            Spark.post(fullPath, internalRequestHandler(route, fullPath));
        } else {
            Spark.post(fullPath, internalRequestHandler(route, fullPath), responseTransformer);
        }
        LOGGER.info("Registered POST route {}", fullPath);
    }

    protected void put(String path, Route route) {
        put(path, route, getResponseTransformer());
    }

    protected void put(String path, Route route, ResponseTransformer responseTransformer) {
        String fullPath = constructPath(path);
        if (responseTransformer == null) {
            Spark.put(fullPath, internalRequestHandler(route, fullPath));
        } else {
            Spark.put(fullPath, internalRequestHandler(route, fullPath), responseTransformer);
        }
        LOGGER.info("Registered PUT route {}", fullPath);
    }

    protected void delete(String path, Route route) {
        String fullPath = constructPath(path);
        Spark.delete(fullPath, internalRequestHandler(route, fullPath));
        LOGGER.info("Registered DELETE route {}", fullPath);
    }

    protected Route getRequestHandler(Route route) {
        return route;
    }

    protected ResponseTransformer getResponseTransformer() {
        return null;
    }

    private Route internalRequestHandler(Route handler, String fullEndpointRoutePattern) {
        return (request, response) -> {
            // New Relic by default uses the strict endpoint url as the transaction name, but we
            // want to group transactions by their pattern and method
            NewRelic.setTransactionName(
                    null,
                    String.format("%s - %s", request.requestMethod(), fullEndpointRoutePattern));
            return getRequestHandler(handler).handle(request, response);
        };
    }

    private String constructPath(String path) {
        return getBasePath() + path;
    }
}
