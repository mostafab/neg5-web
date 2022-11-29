package org.neg5;

import com.google.inject.AbstractModule;
import neg5.accessManager.guice.TournamentAccessGuiceModule;
import neg5.domain.guice.ApiLayerGuiceModule;
import neg5.db.migrations.guice.DatabaseMigrationGuiceModule;
import neg5.exports.qbj.guice.QbjGuiceModule;
import neg5.stats.guice.TournamentStatsModule;
import org.neg5.jwt.module.JwtSigningModule;
import neg5.dataAccess.guice.DataAccessModule;
import neg5.configuration.guice.ConfigurationModule;

public class Neg5WebModule extends AbstractModule {

     @Override
     protected void configure() {
         install(new ConfigurationModule());
         install(new DataAccessModule());
         install(new DatabaseMigrationGuiceModule());
         install(new ApiLayerGuiceModule());
         install(new TournamentStatsModule());
         install(new TournamentAccessGuiceModule());
         install(new QbjGuiceModule());
         install(new ControllersModule());
         install(new FilterModule());
         install(new JwtSigningModule());
     }
}
