package neg5.accessManager.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.accessManager.impl.TournamentAccessManagerImpl;

public class TournamentAccessGuiceModule extends AbstractModule {

    protected void configure() {
        bind(TournamentAccessManager.class).to(TournamentAccessManagerImpl.class)
                .in(Scopes.SINGLETON);
    }
}
