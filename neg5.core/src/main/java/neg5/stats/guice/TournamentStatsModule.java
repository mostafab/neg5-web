package neg5.stats.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import neg5.stats.api.TournamentStatsApi;
import neg5.stats.impl.TournamentStatsApiImpl;
import neg5.stats.impl.cache.TournamentStatsCache;
import neg5.stats.impl.cache.TournamentStatsCacheImpl;
import neg5.stats.api.BaseAggregateStatsDTO;
import neg5.stats.api.FullIndividualMatchesStatsDTO;
import neg5.stats.api.FullTeamsMatchesStatsDTO;
import neg5.stats.api.IndividualStandingsStatsDTO;
import neg5.domain.api.RoundsReportStatsDTO;
import neg5.stats.api.TeamStandingsStatsDTO;

public class TournamentStatsModule extends AbstractModule {

    private static final int MAX_SIZE = 50;
    private static final int MINUTES_TO_KEEP = 5;

    @Override
    protected void configure() {
        bind(TournamentStatsApi.class).to(TournamentStatsApiImpl.class)
                .in(Scopes.SINGLETON);
        bindCaches();
    }

    private void bindCaches() {
        Multibinder<TournamentStatsCache> statsBinder = Multibinder.newSetBinder(binder(), TournamentStatsCache.class);
        statsBinder.addBinding().toInstance(getCache(TeamStandingsStatsDTO.class));
        statsBinder.addBinding().toInstance(getCache(FullTeamsMatchesStatsDTO.class));
        statsBinder.addBinding().toInstance(getCache(IndividualStandingsStatsDTO.class));
        statsBinder.addBinding().toInstance(getCache(FullIndividualMatchesStatsDTO.class));
        statsBinder.addBinding().toInstance(getCache(RoundsReportStatsDTO.class));
    }

    private <T extends BaseAggregateStatsDTO> TournamentStatsCache<T> getCache(Class<T> statsClazz) {
        return new TournamentStatsCacheImpl<>(MAX_SIZE, MINUTES_TO_KEEP, statsClazz);
    }
}
