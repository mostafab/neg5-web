package neg5.service.guice;

import com.google.inject.AbstractModule;
import neg5.accessManager.guice.TournamentAccessGuiceModule;
import neg5.configuration.guice.ConfigurationModule;
import neg5.dataAccess.guice.PersistenceModule;
import neg5.db.migrations.guice.DatabaseMigrationGuiceModule;
import neg5.domain.guice.ApiLayerGuiceModule;
import neg5.exports.qbj.guice.QbjGuiceModule;
import neg5.jwt.module.JwtSigningModule;
import neg5.monitoring.guice.MonitoringGuiceModule;
import neg5.stats.guice.TournamentStatsModule;

public class Neg5WebModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ConfigurationModule());
        install(new MonitoringGuiceModule());
        install(new PersistenceModule());
        install(new DatabaseMigrationGuiceModule());
        install(new ApiLayerGuiceModule());
        install(new TournamentStatsModule());
        install(new TournamentAccessGuiceModule());
        install(new QbjGuiceModule());
        install(new SparkResourcesModule());
        install(new JwtSigningModule());
    }
}
