package neg5.service.controllers;

import static spark.Spark.before;

import com.google.inject.Inject;
import neg5.monitoring.api.MonitoringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ResponseTransformer;
import spark.Route;
import spark.Spark;

public abstract class AbstractRoutes implements BaseRoutes {

    @Inject private MonitoringContext monitoringContext;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract String getBasePath();

    protected Logger getLogger() {
        return logger;
    }

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
        logger.info("Registered GET route {}", fullPath);
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
        logger.info("Registered POST route {}", fullPath);
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
        logger.info("Registered PUT route {}", fullPath);
    }

    protected void delete(String path, Route route) {
        String fullPath = constructPath(path);
        Spark.delete(fullPath, internalRequestHandler(route, fullPath));
        logger.info("Registered DELETE route {}", fullPath);
    }

    protected Route getRequestHandler(Route route) {
        return route;
    }

    protected ResponseTransformer getResponseTransformer() {
        return null;
    }

    private Route internalRequestHandler(Route handler, String fullEndpointRoutePattern) {
        before(
                fullEndpointRoutePattern,
                (request, response) -> {
                    // New Relic by default uses the strict endpoint url as the transaction name,
                    // but we
                    // want to group transactions by their pattern and method. See
                    // https://docs.newrelic.com/docs/apm/agents/java-agent/instrumentation/transaction-naming-protocol/
                    String transactionName =
                            String.format(
                                    "%s - %s", request.requestMethod(), fullEndpointRoutePattern);
                    monitoringContext.setTransactionName(transactionName);
                });
        return (request, response) -> getRequestHandler(handler).handle(request, response);
    }

    private String constructPath(String path) {
        return getBasePath() + path;
    }
}
