package neg5.stats.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Arrays;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPhaseDTO;
import neg5.domain.api.enums.StatReportType;
import neg5.stats.api.StatsCacheInvalidationResultDTO;
import neg5.stats.impl.cache.TournamentStatsCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Stats cache manager */
@Singleton
public class StatsCacheBroker {

    @Inject private TournamentStatsCache tournamentStatsCache;
    @Inject private TournamentPhaseApi phaseManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsCacheBroker.class);

    /**
     * Invalidate cache entries for a tournament's stats. Loops through all phases of a tournament
     * and invalidates entries for each phase id
     *
     * @param tournamentId the tournament id
     */
    public StatsCacheInvalidationResultDTO invalidateStats(String tournamentId) {
        LOGGER.info(
                "Processing request to invalidate cache entries for tournamentId={}", tournamentId);

        StatsCacheInvalidationResultDTO invalidationResult = new StatsCacheInvalidationResultDTO();
        invalidationResult.setTournamentId(tournamentId);

        invalidateTournamentCache(tournamentId, null);
        invalidationResult.setKeysInvalidated(1);
        phaseManager.findAllByTournamentId(tournamentId).stream()
                .map(TournamentPhaseDTO::getId)
                .forEach(
                        phaseId -> {
                            int keysInvalidated = invalidateTournamentCache(tournamentId, phaseId);
                            invalidationResult.setKeysInvalidated(
                                    invalidationResult.getKeysInvalidated() + keysInvalidated);
                        });

        return invalidationResult;
    }

    private int invalidateTournamentCache(String tournamentId, String phaseId) {
        return Arrays.stream(StatReportType.values())
                .mapToInt(
                        reportType -> {
                            tournamentStatsCache.invalidate(reportType, tournamentId, phaseId);
                            return 1;
                        })
                .sum();
    }
}
