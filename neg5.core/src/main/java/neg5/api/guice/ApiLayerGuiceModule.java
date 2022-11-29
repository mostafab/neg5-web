package neg5.api.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import neg5.api.AccountApi;
import neg5.api.MatchPlayerAnswerApi;
import neg5.api.MatchPlayerApi;
import neg5.api.MatchTeamApi;
import neg5.api.TournamentApi;
import neg5.api.TournamentCollaboratorApi;
import neg5.api.TournamentMatchApi;
import neg5.api.TournamentMatchPhaseApi;
import neg5.api.TournamentPhaseApi;
import neg5.api.TournamentPlayerApi;
import neg5.api.TournamentPoolApi;
import neg5.api.TournamentRulesApi;
import neg5.api.TournamentTeamApi;
import neg5.api.TournamentTeamPoolApi;
import neg5.api.TournamentTossupValueApi;
import neg5.api.impl.AccountApiImpl;
import neg5.api.impl.MatchPlayerAnswerApiImpl;
import neg5.api.impl.MatchPlayerApiImpl;
import neg5.api.impl.MatchTeamApiImpl;
import neg5.api.impl.TournamentApiImpl;
import neg5.api.impl.TournamentCollaboratorApiImpl;
import neg5.api.impl.TournamentMatchApiImpl;
import neg5.api.impl.TournamentMatchPhaseApiImpl;
import neg5.api.impl.TournamentPhaseApiImpl;
import neg5.api.impl.TournamentPlayerApiImpl;
import neg5.api.impl.TournamentPoolApiImpl;
import neg5.api.impl.TournamentRulesApiImpl;
import neg5.api.impl.TournamentTeamApiImpl;
import neg5.api.impl.TournamentTeamPoolApiImpl;
import neg5.api.impl.TournamentTossupValueApiImpl;

public class ApiLayerGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TournamentMatchApi.class, TournamentMatchApiImpl.class);
        bind(TournamentTeamApi.class, TournamentTeamApiImpl.class);
        bind(TournamentPlayerApi.class, TournamentPlayerApiImpl.class);
        bind(TournamentTossupValueApi.class, TournamentTossupValueApiImpl.class);
        bind(TournamentPhaseApi.class, TournamentPhaseApiImpl.class);
        bind(TournamentPoolApi.class, TournamentPoolApiImpl.class);
        bind(TournamentTeamPoolApi.class, TournamentTeamPoolApiImpl.class);
        bind(TournamentMatchPhaseApi.class, TournamentMatchPhaseApiImpl.class);
        bind(AccountApi.class, AccountApiImpl.class);
        bind(TournamentCollaboratorApi.class, TournamentCollaboratorApiImpl.class);
        bind(MatchTeamApi.class, MatchTeamApiImpl.class);
        bind(MatchPlayerApi.class, MatchPlayerApiImpl.class);
        bind(MatchPlayerAnswerApi.class, MatchPlayerAnswerApiImpl.class);
        bind(TournamentApi.class, TournamentApiImpl.class);

        bind(TournamentRulesApi.class, TournamentRulesApiImpl.class);
    }

    private <T, X extends T> void bind(Class<T> api, Class<X> impl) {
        bind(api).to(impl).in(Scopes.SINGLETON);
    }
}
