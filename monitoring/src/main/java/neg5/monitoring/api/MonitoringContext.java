package neg5.monitoring.api;

import java.util.Map;
import javax.annotation.Nonnull;

public interface MonitoringContext {

    @Nonnull
    Map<String, String> getContext();

    void put(@Nonnull String key, String value);

    void setTransactionName(String name);

    void clear();
}
