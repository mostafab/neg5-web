package neg5.monitoring.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.newrelic.api.agent.NewRelic;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import neg5.monitoring.api.MonitoringContext;

@Singleton
public class ThreadLocalMonitoringContext implements MonitoringContext {

    private static final ThreadLocal<Map<String, String>> CONTEXT_TL =
            ThreadLocal.withInitial(HashMap::new);

    @Override
    @Nonnull
    public Map<String, String> getContext() {
        return ImmutableMap.copyOf(CONTEXT_TL.get());
    }

    @Override
    public void put(@Nonnull String key, String value) {
        Objects.requireNonNull(key, "key cannot be null");
        CONTEXT_TL.get().put(key, value);
    }

    @Override
    public void setTransactionName(String name) {
        NewRelic.setTransactionName(null, name);
    }

    @Override
    public void clear() {
        CONTEXT_TL.remove();
    }
}
