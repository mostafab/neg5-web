package org.neg5;

import com.google.inject.AbstractModule;
import neg5.db.flyway.module.FlywayModule;
import org.neg5.jwt.module.JwtSigningModule;
import org.neg5.matchValidators.MatchValidatorsModule;
import org.neg5.module.DataAccessModule;
import org.neg5.module.StatsCacheModule;
import org.neg5.module.ConfigurationModule;

public class Neg5WebModule extends AbstractModule {

     @Override
     protected void configure() {
         install(new ConfigurationModule());
         install(new DataAccessModule());
         install(new FlywayModule());
         install(new ControllersModule());
         install(new FilterModule());
         install(new JwtSigningModule());
         install(new StatsCacheModule());
         install(new MatchValidatorsModule());
     }
}
