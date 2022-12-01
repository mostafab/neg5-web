package neg5.monitoring.api;

import javax.annotation.Nullable;

public interface ExceptionAlerter {

    void notifyOfException(@Nullable Throwable error);
}
