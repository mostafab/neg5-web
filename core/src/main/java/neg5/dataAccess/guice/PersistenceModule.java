package neg5.dataAccess.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import neg5.lifecycle.ApplicationShutdownHandler;
import org.hibernate.cfg.AvailableSettings;

public class PersistenceModule extends AbstractModule {

    private static final String PERSISTENCE_UNIT_NAME = "org.neg5.data";
    private final Map<String, Object> properties;

    public PersistenceModule() {
        properties = new HashMap<>();
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(PERSISTENCE_UNIT_NAME).properties(properties));
    }

    @Provides
    @Singleton
    public DataSource provideDataSource(
            @Named("database.username") String username,
            @Named("database.password") String password,
            @Named("database.jdbcUrl") String jdbcUrl,
            @Named("database.pool.size") Integer poolSize,
            @Named("database.pool.name") String poolName,
            @Named("database.driver") String driverClass,
            ApplicationShutdownHandler shutdownHandler) {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName(driverClass);
        config.setMaximumPoolSize(poolSize);
        config.setPoolName(poolName);
        config.setReadOnly(false);

        HikariDataSource dataSource = new HikariDataSource(config);
        // This is kind of ugly, but we need to set the data source on the properties attached to
        // the JpaPersistModule
        properties.put(AvailableSettings.DATASOURCE, dataSource);

        shutdownHandler.registerCloseable(dataSource);
        return dataSource;
    }
}
