package neg5.stats.impl.cache;

import com.google.common.cache.Cache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import neg5.domain.api.enums.StatReportType;
import neg5.stats.api.BaseAggregateStatsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Guava-backed implementation of {@link TournamentStatsCache} */
@Singleton
public class TournamentStatsCacheImpl implements TournamentStatsCache {

    private final Cache<String, BaseAggregateStatsDTO> cache;

    private final Logger LOGGER = LoggerFactory.getLogger(TournamentStatsCacheImpl.class);

    @Inject
    public TournamentStatsCacheImpl(Cache<String, BaseAggregateStatsDTO> cache) {
        this.cache = cache;
    }

    @Override
    public <T extends BaseAggregateStatsDTO> T getOrAdd(
            StatReportType reportType,
            String tournamentId,
            String phaseId,
            Supplier<T> fallbackCacheSupplier) {
        try {
            BaseAggregateStatsDTO stats =
                    cache.get(
                            buildKey(reportType, tournamentId, phaseId),
                            () -> {
                                LOGGER.info(
                                        "Running stats calculation for reportType={}, tournament={}, phase={}",
                                        reportType.getId(),
                                        tournamentId,
                                        phaseId);
                                return fallbackCacheSupplier.get();
                            });
            return (T) stats;
        } catch (ExecutionException e) {
            String message =
                    String.format(
                            "Encountered exception retrieving stats from cache for tournament=%s, phase=%s, type=%s",
                            tournamentId, phaseId, reportType.getId());
            LOGGER.error(message, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void invalidate(StatReportType reportType, String tournamentId, String phaseId) {
        String key = buildKey(reportType, tournamentId, phaseId);
        cache.invalidate(key);
    }

    private String buildKey(StatReportType reportType, String tournamentId, String phaseId) {
        if (phaseId == null) {
            return String.format("%s_%s", reportType.getId(), tournamentId);
        }
        return String.format("%s_%s_%s", reportType.getId(), tournamentId, phaseId);
    }
}
