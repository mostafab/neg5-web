package neg5.stats.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import neg5.stats.api.TournamentStatsApi;
import neg5.stats.impl.TournamentStatsApiImpl;
import neg5.stats.impl.cache.guice.StatsCacheGuiceModule;

public class TournamentStatsGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TournamentStatsApi.class).to(TournamentStatsApiImpl.class).in(Scopes.SINGLETON);

        install(new StatsCacheGuiceModule());
    }
}
