package neg5.stats.impl.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.neg5.BaseAggregateStatsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Guava-backed implementation of {@link TournamentStatsCache}
 * @param <T> the stats type
 */
public class TournamentStatsCacheImpl<T extends BaseAggregateStatsDTO> implements TournamentStatsCache<T> {

    private final Cache<String, T> cache;
    private final Class<T> statsClazz;

    private final Logger LOGGER = LoggerFactory.getLogger(TournamentStatsCacheImpl.class);

    public TournamentStatsCacheImpl(int maxSize, int maxMinutes, Class<T> statsClazz) {
        this.statsClazz = statsClazz;
        cache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(maxMinutes, TimeUnit.MINUTES)
                .removalListener(removalNotification -> {
                    LOGGER.info("Removed key {} from the {} cache", removalNotification.getKey(), statsClazz);
                })
                .build();
    }

    @Override
    public Class<T> getStatsClazz() {
        return statsClazz;
    }

    @Override
    public Optional<T> getOrAdd(String tournamentId, String phaseId, Supplier<T> fallbackCacheSupplier) {
        try {
            return Optional.ofNullable(cache.get(buildKey(tournamentId, phaseId), () -> {
                LOGGER.info("Running stats calculation for clazz {} and tournament {} and phase {}",
                        statsClazz, tournamentId, phaseId);
                return fallbackCacheSupplier.get();
            }));
        } catch (ExecutionException e) {
            LOGGER.error("Encountered exception attempting to get stats from cache for tournament " + tournamentId, e);
            return Optional.empty();
        }
    }

    @Override
    public void invalidate(String tournamentId, String phaseId) {
        String key = buildKey(tournamentId, phaseId);
        cache.invalidate(key);
    }

    private String buildKey(String tournamentId, String phaseId) {
        if (phaseId == null) {
            return tournamentId;
        }
        return tournamentId + "_" + phaseId;
    }
}
