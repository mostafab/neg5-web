package neg5.monitoring.impl;

import javax.annotation.Nullable;
import neg5.monitoring.api.ExceptionAlerter;

public class NoopAlerter implements ExceptionAlerter {

    @Override
    public void notifyOfException(@Nullable Throwable error) {
        // Nothing
    }
}
