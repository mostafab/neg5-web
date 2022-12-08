package neg5.stats.impl.cache.guice;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.util.concurrent.TimeUnit;
import neg5.stats.api.BaseAggregateStatsDTO;
import neg5.stats.impl.cache.TournamentStatsCache;
import neg5.stats.impl.cache.TournamentStatsCacheImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatsCacheGuiceModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsCacheGuiceModule.class);

    @Override
    protected void configure() {
        bind(TournamentStatsCache.class).to(TournamentStatsCacheImpl.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public Cache<String, BaseAggregateStatsDTO> provideCache(
            @Named("stats.cache.maxSize") int maxSize,
            @Named("stats.cache.expireMinutes") int expireMinutes) {
        LOGGER.info(
                "Instantiating Guava cache with maxSize={} and expireMinutes={}",
                maxSize,
                expireMinutes);
        return CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireMinutes, TimeUnit.MINUTES)
                .removalListener(
                        removalNotification -> {
                            LOGGER.info(
                                    "Removed key={} from the stats cache. reason={}",
                                    removalNotification.getKey(),
                                    removalNotification.getCause());
                        })
                .build();
    }
}
