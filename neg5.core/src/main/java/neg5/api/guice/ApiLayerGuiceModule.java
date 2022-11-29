package neg5.api.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import neg5.api.ObjectApiLayer;
import neg5.api.TournamentMatchApi;
import neg5.api.TournamentPlayerApi;
import neg5.api.TournamentTeamApi;
import org.neg5.managers.TournamentMatchManager;
import org.neg5.managers.TournamentPlayerManager;
import org.neg5.managers.TournamentTeamManager;

public class ApiLayerGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TournamentMatchApi.class, TournamentMatchManager.class);
        bind(TournamentTeamApi.class, TournamentTeamManager.class);
        bind(TournamentPlayerApi.class, TournamentPlayerManager.class);
    }

    private <T extends ObjectApiLayer<?, ?>, X extends T> void bind(Class<T> api, Class<X> impl) {
        bind(api).to(impl).in(Scopes.SINGLETON);
    }
}
