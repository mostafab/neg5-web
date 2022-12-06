package neg5.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import neg5.dataAccess.PersistenceInitializer;
import neg5.db.migrations.api.DatabaseMigrator;
import neg5.service.controllers.RouteRegistry;
import neg5.service.exceptionHandlers.ExceptionHandlerRegistry;
import neg5.service.filters.FilterRegistry;
import spark.Spark;
import spark.servlet.SparkApplication;

public class Neg5App implements SparkApplication {

    @Inject private RouteRegistry routeRegistry;
    @Inject private FilterRegistry filterRegistry;
    @Inject private ExceptionHandlerRegistry exceptionHandlerRegistry;

    @Inject
    @Named("service.port")
    private Integer port;

    @Inject private DatabaseMigrator databaseMigrator;

    @Inject private PersistenceInitializer persistenceInitializer;

    @Override
    public synchronized void init() {
        persistenceInitializer.start();

        // Migrate DB before server starts
        databaseMigrator.migrateDatabase();

        Spark.port(port);
        initRoutes();
        initFilters();
        initExceptionHandlers();
    }

    private void initRoutes() {
        routeRegistry.initRoutes();
    }

    private void initFilters() {
        filterRegistry.initFilters();
    }

    private void initExceptionHandlers() {
        exceptionHandlerRegistry.initExceptionHandlers();
    }
}
