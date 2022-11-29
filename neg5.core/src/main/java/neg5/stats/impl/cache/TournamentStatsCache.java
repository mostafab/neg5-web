package neg5.stats.impl.cache;

import neg5.stats.api.BaseAggregateStatsDTO;

import java.util.Optional;
import java.util.function.Supplier;

public interface TournamentStatsCache<T extends BaseAggregateStatsDTO> {

    Class<T> getStatsClazz();

    Optional<T> getOrAdd(String tournamentId, String phaseId, Supplier<T> fallbackCacheSupplier);

    void invalidate(String tournamentId, String phaseId);
}
