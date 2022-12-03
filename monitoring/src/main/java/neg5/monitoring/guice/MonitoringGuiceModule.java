package neg5.monitoring.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import neg5.monitoring.api.ExceptionAlerter;
import neg5.monitoring.api.MonitoringContext;
import neg5.monitoring.impl.BugsnagAlerter;
import neg5.monitoring.impl.NoopAlerter;
import neg5.monitoring.impl.ThreadLocalMonitoringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringGuiceModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringGuiceModule.class);

    @Override
    protected void configure() {
        bind(MonitoringContext.class).to(ThreadLocalMonitoringContext.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public ExceptionAlerter provideExceptionAlerter(
            @Named("bugsnag.apiKey") String bugsnagApiKey, MonitoringContext monitoringContext) {
        if (!bugsnagApiKey.isEmpty()) {
            return new BugsnagAlerter(bugsnagApiKey, monitoringContext);
        }
        LOGGER.info("bugsnag.apiKey property was empty. Defaulting to no-op exception alerter.");
        return new NoopAlerter();
    }
}
