package neg5.monitoring.impl;

import com.bugsnag.Bugsnag;
import com.bugsnag.Report;
import javax.annotation.Nullable;
import neg5.monitoring.api.ExceptionAlerter;
import neg5.monitoring.api.MonitoringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BugsnagAlerter implements ExceptionAlerter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BugsnagAlerter.class);

    private final Bugsnag bugsnag;
    private final MonitoringContext monitoringContext;

    public BugsnagAlerter(String apiKey, MonitoringContext monitoringContext) {
        this.bugsnag = new Bugsnag(apiKey);
        this.monitoringContext = monitoringContext;
    }

    @Override
    public void notifyOfException(@Nullable Throwable error) {
        if (error == null) {
            return;
        }
        try {
            Report report = bugsnag.buildReport(error);
            monitoringContext
                    .getContext()
                    .forEach(
                            (key, value) -> {
                                report.addToTab("request_metadata", key, value);
                            });
            bugsnag.notify(report);
        } catch (Exception ex) {
            LOGGER.error("Encountered exception attempting to send alert to Bugsnag", ex);
        }
    }
}
