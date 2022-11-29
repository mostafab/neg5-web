package neg5.db.migrations.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.db.migrations.api.DatabaseMigrator;
import org.flywaydb.core.Flyway;
import org.neg5.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class FlywayDatabaseMigrator implements DatabaseMigrator {

    private final Flyway flyway;
    private final Environment environment;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayDatabaseMigrator.class);

    @Inject
    protected FlywayDatabaseMigrator(Flyway flyway, Environment environment) {
        this.flyway = flyway;
        this.environment = environment;
    }

    public void migrateDatabase() {
        if (environment != Environment.DEV) {
            flyway.migrate();
        } else {
            LOGGER.warn("Skipping automatic migrations since application is in the DEV environment. "
                    + "Please run migrations manually by running 'mvn flyway:migrate' in the neg5.db module");
        }
    }
}
