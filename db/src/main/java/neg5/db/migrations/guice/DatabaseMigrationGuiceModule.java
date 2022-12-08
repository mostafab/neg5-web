package neg5.db.migrations.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import javax.sql.DataSource;
import neg5.db.migrations.api.DatabaseMigrator;
import neg5.db.migrations.impl.FlywayDatabaseMigrator;
import org.flywaydb.core.Flyway;

public class DatabaseMigrationGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DatabaseMigrator.class).to(FlywayDatabaseMigrator.class).in(Scopes.SINGLETON);
    }

    @Provides
    public Flyway provideFlyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .locations("classpath:migrations")
                .load();
    }
}
