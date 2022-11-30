package neg5.stats.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Set;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPhaseDTO;
import neg5.stats.api.BaseAggregateStatsDTO;
import neg5.stats.api.StatsCacheInvalidationResultDTO;
import neg5.stats.impl.cache.TournamentStatsCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Stats cache manager */
@Singleton
public class StatsCacheManager {

    @Inject private Set<TournamentStatsCache> statsCaches;
    @Inject private TournamentPhaseApi phaseManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsCacheManager.class);

    /**
     * Invalidate cache entries for a tournament's stats. Loops through all phases of a tournament
     * and invalidates entries for each phase id
     *
     * @param tournamentId the tournament id
     */
    public StatsCacheInvalidationResultDTO invalidateStats(String tournamentId) {
        LOGGER.info("Received request to invalidate cache entries for tournament {}", tournamentId);

        StatsCacheInvalidationResultDTO invalidationResult = new StatsCacheInvalidationResultDTO();
        invalidationResult.setTournamentId(tournamentId);

        phaseManager.findAllByTournamentId(tournamentId).stream()
                .map(TournamentPhaseDTO::getId)
                .forEach(
                        phaseId -> {
                            statsCaches.forEach(cache -> cache.invalidate(tournamentId, phaseId));
                            invalidationResult.setKeysInvalidated(
                                    invalidationResult.getKeysInvalidated() + 1);
                        });

        statsCaches.forEach(cache -> cache.invalidate(tournamentId, null));
        invalidationResult.setKeysInvalidated(invalidationResult.getKeysInvalidated() + 1);

        return invalidationResult;
    }

    <T extends BaseAggregateStatsDTO> TournamentStatsCache<T> getCache(Class<T> statsClazz) {
        return (TournamentStatsCache<T>)
                statsCaches.stream()
                        .filter(cache -> statsClazz.equals(cache.getStatsClazz()))
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Cannot find matching cache for class "
                                                        + statsClazz));
    }
}
