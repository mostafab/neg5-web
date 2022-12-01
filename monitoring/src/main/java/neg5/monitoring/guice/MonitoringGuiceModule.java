package neg5.monitoring.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import neg5.domain.api.Environment;
import neg5.monitoring.api.ExceptionAlerter;
import neg5.monitoring.impl.BugsnagAlerter;
import neg5.monitoring.impl.NoopAlerter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringGuiceModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringGuiceModule.class);

    @Provides
    @Singleton
    public ExceptionAlerter provideExceptionAlerter(Environment environment, @Named("bugsnag.apiKey") String bugsnagApiKey) {
        if (environment == Environment.PRODUCTION && !bugsnagApiKey.isEmpty()) {
            return new BugsnagAlerter(bugsnagApiKey);
        }
        LOGGER.info("bugsnag.apiKey property was empty or we're not in production. Defaulting to no-op exception alerter.");
        return new NoopAlerter();
    }
}
