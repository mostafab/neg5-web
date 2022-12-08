package neg5.stats.impl.cache;

import java.util.function.Supplier;
import neg5.domain.api.enums.StatReportType;
import neg5.stats.api.BaseAggregateStatsDTO;

public interface TournamentStatsCache {

    <T extends BaseAggregateStatsDTO> T getOrAdd(
            StatReportType reportType,
            String tournamentId,
            String phaseId,
            Supplier<T> fallbackCacheSupplier);

    void invalidate(StatReportType reportType, String tournamentId, String phaseId);
}
