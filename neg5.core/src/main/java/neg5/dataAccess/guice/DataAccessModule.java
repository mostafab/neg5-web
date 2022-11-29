package neg5.dataAccess.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataAccessModule extends AbstractModule {

    private static final String PERSISTENCE_UNIT_NAME = "org.neg5.data";
    private final Map<String, Object> properties;

    public DataAccessModule() {
        properties = new HashMap<>();
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(PERSISTENCE_UNIT_NAME).properties(properties));
    }

    @Provides
    @Singleton
    public DataSource provideDataSource(@Named("database.username") String username,
                                        @Named("database.password") String password,
                                        @Named("database.jdbcUrl") String jdbcUrl,
                                        @Named("database.pool.size") Integer poolSize,
                                        @Named("database.pool.name") String poolName,
                                        @Named("database.driver") String driverClass) {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName(driverClass);
        config.setMaximumPoolSize(poolSize);
        config.setPoolName(poolName);
        config.setReadOnly(false);

        DataSource dataSource = new HikariDataSource(config);
        // This is kind of ugly, but we need to set the data source on the properties attached to the JpaPersistModule
        properties.put(AvailableSettings.DATASOURCE, dataSource);

        return dataSource;
    }
}
