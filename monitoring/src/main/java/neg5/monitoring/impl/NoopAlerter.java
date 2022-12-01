package neg5.monitoring.impl;

import neg5.monitoring.api.ExceptionAlerter;

import javax.annotation.Nullable;

public class NoopAlerter implements ExceptionAlerter {

    @Override
    public void notifyOfException(@Nullable Throwable error) {
        // Nothing
    }
}
