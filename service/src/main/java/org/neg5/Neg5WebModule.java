package org.neg5;

import com.google.inject.AbstractModule;
import neg5.db.flyway.module.FlywayModule;
import org.neg5.jwt.module.JwtSigningModule;
import org.neg5.module.DataAccessModule;
import org.neg5.module.StatsCacheModule;
import org.neg5.module.SystemPropertiesModule;

public class Neg5WebModule extends AbstractModule {

     @Override
     protected void configure() {
         install(new SystemPropertiesModule());
         install(new DataAccessModule());
         install(new FlywayModule());
         install(new ControllersModule());
         install(new FilterModule());
         install(new JwtSigningModule("NEG5_JWT"));
         install(new StatsCacheModule());
     }
}
