package neg5.service.guice;

import com.google.inject.AbstractModule;
import neg5.accessManager.guice.TournamentAccessGuiceModule;
import neg5.configuration.guice.ConfigurationGuiceModule;
import neg5.dataAccess.guice.PersistenceGuiceModule;
import neg5.db.migrations.guice.DatabaseMigrationGuiceModule;
import neg5.domain.guice.ApiLayerGuiceModule;
import neg5.exports.qbj.guice.QbjGuiceModule;
import neg5.jwt.module.JwtGuiceModule;
import neg5.monitoring.guice.MonitoringGuiceModule;
import neg5.stats.guice.TournamentStatsGuiceModule;

public class Neg5WebGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ConfigurationGuiceModule());
        install(new MonitoringGuiceModule());
        install(new PersistenceGuiceModule());
        install(new DatabaseMigrationGuiceModule());
        install(new ApiLayerGuiceModule());
        install(new TournamentStatsGuiceModule());
        install(new TournamentAccessGuiceModule());
        install(new QbjGuiceModule());
        install(new SparkResourcesGuiceModule());
        install(new JwtGuiceModule());
    }
}
