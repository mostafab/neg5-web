package neg5.stats.impl.cache.guice;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.util.concurrent.TimeUnit;
import neg5.stats.api.BaseAggregateStatsDTO;

public class StatsCacheGuiceModule extends AbstractModule {

    @Provides
    @Singleton
    public Cache<String, BaseAggregateStatsDTO> provideCache(
            @Named("stats.cache.maxSize") int maxSize,
            @Named("stats.cache.expireMinutes") int expireMinutes) {
        return CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireMinutes, TimeUnit.MINUTES)
                .removalListener(removalNotification -> {})
                .build();
    }
}
