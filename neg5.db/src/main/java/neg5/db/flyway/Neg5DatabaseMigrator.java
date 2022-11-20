package neg5.db.flyway;

import com.google.inject.Inject;
import org.flywaydb.core.Flyway;
import org.neg5.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neg5DatabaseMigrator {

    private final Flyway flyway;

    private static final Logger LOGGER = LoggerFactory.getLogger(Neg5DatabaseMigrator.class);

    @Inject
    protected Neg5DatabaseMigrator(Flyway flyway) {
        this.flyway = flyway;
    }

    public void migrateDatabase() {
        if (Environment.getEnvironment() != Environment.DEV) {
            flyway.migrate();
        } else {
            LOGGER.warn("Skipping automatic migrations since application is in the DEV environment. "
                    + "Please run migrations manually by running 'mvn flyway:migrate' in the neg5.db module");
        }
    }
}
