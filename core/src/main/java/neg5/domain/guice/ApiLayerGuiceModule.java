package neg5.domain.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import neg5.domain.api.AccountApi;
import neg5.domain.api.MatchPlayerAnswerApi;
import neg5.domain.api.MatchPlayerApi;
import neg5.domain.api.MatchTeamApi;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentCollaboratorApi;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchPhaseApi;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPlayerApi;
import neg5.domain.api.TournamentPoolApi;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentScheduleMatchApi;
import neg5.domain.api.TournamentSchedulingApi;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.api.TournamentScoresheetCycleAnswerApi;
import neg5.domain.api.TournamentScoresheetCycleApi;
import neg5.domain.api.TournamentScoresheetCycleBonusApi;
import neg5.domain.api.TournamentSearchApi;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamGroupApi;
import neg5.domain.api.TournamentTeamPoolApi;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.impl.AccountApiImpl;
import neg5.domain.impl.MatchPlayerAnswerApiImpl;
import neg5.domain.impl.MatchPlayerApiImpl;
import neg5.domain.impl.MatchTeamApiImpl;
import neg5.domain.impl.TournamentApiImpl;
import neg5.domain.impl.TournamentCollaboratorApiImpl;
import neg5.domain.impl.TournamentMatchApiImpl;
import neg5.domain.impl.TournamentMatchPhaseApiImpl;
import neg5.domain.impl.TournamentPhaseApiImpl;
import neg5.domain.impl.TournamentPlayerApiImpl;
import neg5.domain.impl.TournamentPoolApiImpl;
import neg5.domain.impl.TournamentRulesApiImpl;
import neg5.domain.impl.TournamentScheduleMatchApiImpl;
import neg5.domain.impl.TournamentSchedulingApiImpl;
import neg5.domain.impl.TournamentScoresheetApiImpl;
import neg5.domain.impl.TournamentScoresheetCycleAnswerApiImpl;
import neg5.domain.impl.TournamentScoresheetCycleApiImpl;
import neg5.domain.impl.TournamentScoresheetCycleBonusApiImpl;
import neg5.domain.impl.TournamentSearchApiImpl;
import neg5.domain.impl.TournamentTeamApiImpl;
import neg5.domain.impl.TournamentTeamGroupApiImpl;
import neg5.domain.impl.TournamentTeamPoolApiImpl;
import neg5.domain.impl.TournamentTossupValueApiImpl;

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
        bind(TournamentSearchApi.class, TournamentSearchApiImpl.class);
        bind(TournamentScoresheetApi.class, TournamentScoresheetApiImpl.class);
        bind(TournamentScoresheetCycleApi.class, TournamentScoresheetCycleApiImpl.class);
        bind(
                TournamentScoresheetCycleAnswerApi.class,
                TournamentScoresheetCycleAnswerApiImpl.class);
        bind(TournamentScoresheetCycleBonusApi.class, TournamentScoresheetCycleBonusApiImpl.class);
        bind(TournamentSchedulingApi.class, TournamentSchedulingApiImpl.class);
        bind(TournamentScheduleMatchApi.class, TournamentScheduleMatchApiImpl.class);
        bind(TournamentTeamGroupApi.class, TournamentTeamGroupApiImpl.class);
    }

    private <T, X extends T> void bind(Class<T> api, Class<X> impl) {
        bind(api).to(impl).in(Scopes.SINGLETON);
    }
}
