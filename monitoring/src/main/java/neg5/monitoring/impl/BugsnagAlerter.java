package neg5.monitoring.impl;

import com.bugsnag.Bugsnag;
import neg5.monitoring.api.ExceptionAlerter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

public class BugsnagAlerter implements ExceptionAlerter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BugsnagAlerter.class);

    private final Bugsnag bugsnag;

    public BugsnagAlerter(String apiKey) {
        this.bugsnag = new Bugsnag(apiKey);
    }

    @Override
    public void notifyOfException(@Nullable Throwable error) {
        if (error == null) {
            return;
        }
        try {
            bugsnag.notify(error);
        } catch (Exception ex) {
            LOGGER.error("Encountered exception attempting to send alert to Bugsnag", ex);
        }
    }
}
