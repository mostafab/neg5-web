package neg5.db.flyway.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayModule extends AbstractModule {

    @Provides
    public Flyway provideFlyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .locations("classpath:migrations")
                .load();
    }
}
