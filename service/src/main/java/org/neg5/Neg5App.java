package org.neg5;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import neg5.db.flyway.Neg5DatabaseMigrator;
import org.neg5.core.PersistInitializer;
import org.neg5.util.FilterRegistry;
import spark.Spark;
import spark.servlet.SparkApplication;

public class Neg5App implements SparkApplication {

    @Inject private ControllerRegistry controllerRegistry;
    @Inject private FilterRegistry filterRegistry;
    @Inject @Named("service.port") private Integer port;
    @Inject private Neg5DatabaseMigrator databaseMigrator;

    @Inject private PersistInitializer persistInitializer;

    @Override
    public synchronized void init() {
        persistInitializer.start();

        Spark.port(port);
        initRoutes();
        initFilters();

        // Migrate DB after server starts
        databaseMigrator.migrateDatabase();
    }

    private void initRoutes() {
        controllerRegistry.initControllers();
    }

    private void initFilters() {
        filterRegistry.initFilters();
    }
}
