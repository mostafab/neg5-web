package neg5.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import neg5.db.migrations.api.DatabaseMigrator;
import neg5.dataAccess.PersistenceInitializer;
import neg5.service.controllers.ControllerRegistry;
import neg5.service.filters.FilterRegistry;
import spark.Spark;
import spark.servlet.SparkApplication;

public class Neg5App implements SparkApplication {

    @Inject private ControllerRegistry controllerRegistry;
    @Inject private FilterRegistry filterRegistry;
    @Inject @Named("service.port") private Integer port;
    @Inject private DatabaseMigrator databaseMigrator;

    @Inject private PersistenceInitializer persistenceInitializer;

    @Override
    public synchronized void init() {
        persistenceInitializer.start();

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
