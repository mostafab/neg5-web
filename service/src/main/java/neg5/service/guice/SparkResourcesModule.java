package neg5.service.guice;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.util.List;
import neg5.service.controllers.AccountController;
import neg5.service.controllers.BaseController;
import neg5.service.controllers.LoginController;
import neg5.service.controllers.MatchController;
import neg5.service.controllers.PlayerController;
import neg5.service.controllers.TeamController;
import neg5.service.controllers.TournamentCollaboratorController;
import neg5.service.controllers.TournamentController;
import neg5.service.controllers.TournamentPhaseController;
import neg5.service.controllers.TournamentPoolController;
import neg5.service.controllers.TournamentStatsController;
import neg5.service.controllers.TournamentTossupValueController;
import neg5.service.exceptionHandlers.ExceptionHandler;
import neg5.service.exceptionHandlers.GeneralExceptionHandler;
import neg5.service.exceptionHandlers.NoResultHandler;
import neg5.service.exceptionHandlers.ObjectValidationExceptionHandler;
import neg5.service.exceptionHandlers.TournamentAccessExceptionHandler;
import neg5.service.filters.CurrentUserContextFilter;
import neg5.service.filters.MonitoringContextFilter;
import neg5.service.filters.RequestFilter;

public class SparkResourcesModule extends AbstractModule {

    @Provides
    @Singleton
    public List<BaseController> provideControllers(
            AccountController accountController,
            LoginController loginController,
            MatchController matchController,
            PlayerController playerController,
            TeamController teamController,
            TournamentCollaboratorController tournamentCollaboratorController,
            TournamentController tournamentController,
            TournamentPhaseController phaseController,
            TournamentPoolController poolController,
            TournamentStatsController statsController,
            TournamentTossupValueController tournamentTossupValueController) {
        return ImmutableList.of(
                accountController,
                loginController,
                matchController,
                playerController,
                teamController,
                tournamentCollaboratorController,
                tournamentController,
                phaseController,
                poolController,
                statsController,
                tournamentTossupValueController);
    }

    @Provides
    @Singleton
    public List<RequestFilter> provideRequestFilters(
            CurrentUserContextFilter currentUserContextFilter,
            MonitoringContextFilter monitoringContextFilter) {
        return ImmutableList.of(currentUserContextFilter, monitoringContextFilter);
    }

    @Provides
    @Singleton
    public List<ExceptionHandler> provideExceptionHandlers(
            NoResultHandler noResultHandler,
            TournamentAccessExceptionHandler tournamentAccessExceptionHandler,
            ObjectValidationExceptionHandler objectValidationExceptionHandler,
            GeneralExceptionHandler generalExceptionHandler) {
        return ImmutableList.of(
                noResultHandler,
                tournamentAccessExceptionHandler,
                objectValidationExceptionHandler,
                generalExceptionHandler);
    }
}
