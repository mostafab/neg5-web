package org.neg5.controllers;

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
            Spark.get(fullPath, enrichRoute(route));
        } else {
            Spark.get(fullPath, enrichRoute(route), responseTransformer);
        }
        LOGGER.info("Mapped GET route {}", fullPath);
    }

    protected void post(String path, Route route) {
        post(path, route, getResponseTransformer());
    }

    protected void post(String path, Route route, ResponseTransformer responseTransformer) {
        String fullPath = constructPath(path);
        if (responseTransformer == null) {
            Spark.post(fullPath, enrichRoute(route));
        } else {
            Spark.post(fullPath, enrichRoute(route), responseTransformer);
        }
        LOGGER.info("Mapped POST route {}", fullPath);
    }

    protected void put(String path, Route route) {
        put(path, route, getResponseTransformer());
    }

    protected void put(String path, Route route, ResponseTransformer responseTransformer) {
        String fullPath = constructPath(path);
        if (responseTransformer == null) {
            Spark.put(fullPath, enrichRoute(route));
        } else {
            Spark.put(fullPath, enrichRoute(route), responseTransformer);
        }
        LOGGER.info("Mapped PUT route {}", fullPath);
    }

    protected Route enrichRoute(Route route) {
        return route;
    }

    protected ResponseTransformer getResponseTransformer() {
        return null;
    }

    private String constructPath(String path) {
        return getBasePath() + path;
    }
}
