package neg5.service.guice;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.util.List;
import neg5.service.controllers.AccountRoutes;
import neg5.service.controllers.BaseRoutes;
import neg5.service.controllers.LoginRoutes;
import neg5.service.controllers.PusherAuthRoutes;
import neg5.service.controllers.TournamentCollaboratorRoutes;
import neg5.service.controllers.TournamentMatchRoutes;
import neg5.service.controllers.TournamentPhaseRoutes;
import neg5.service.controllers.TournamentPlayerRoutes;
import neg5.service.controllers.TournamentPoolRoutes;
import neg5.service.controllers.TournamentRoutes;
import neg5.service.controllers.TournamentScheduleRoutes;
import neg5.service.controllers.TournamentScoresheetRoutes;
import neg5.service.controllers.TournamentSearchRoutes;
import neg5.service.controllers.TournamentStatsRoutes;
import neg5.service.controllers.TournamentTeamPoolRoutes;
import neg5.service.controllers.TournamentTeamRoutes;
import neg5.service.controllers.TournamentTossupValueRoutes;
import neg5.service.exceptionHandlers.ExceptionHandler;
import neg5.service.exceptionHandlers.GeneralExceptionHandler;
import neg5.service.exceptionHandlers.NoResultHandler;
import neg5.service.exceptionHandlers.ObjectValidationExceptionHandler;
import neg5.service.exceptionHandlers.TournamentAccessExceptionHandler;
import neg5.service.filters.CurrentUserContextFilter;
import neg5.service.filters.MonitoringContextFilter;
import neg5.service.filters.RequestFilter;

public class SparkResourcesGuiceModule extends AbstractModule {

    @Provides
    @Singleton
    public List<BaseRoutes> provideRoutes(
            PusherAuthRoutes pusherAuthRoutes,
            AccountRoutes accountController,
            LoginRoutes loginController,
            TournamentMatchRoutes matchController,
            TournamentPlayerRoutes playerController,
            TournamentTeamRoutes teamController,
            TournamentCollaboratorRoutes tournamentCollaboratorController,
            TournamentRoutes tournamentController,
            TournamentPhaseRoutes phaseController,
            TournamentPoolRoutes poolController,
            TournamentStatsRoutes statsController,
            TournamentTossupValueRoutes tournamentTossupValueController,
            TournamentSearchRoutes tournamentSearchRoutes,
            TournamentTeamPoolRoutes teamPoolRoutes,
            TournamentScoresheetRoutes scoresheetRoutes,
            TournamentScheduleRoutes scheduleRoutes) {
        return ImmutableList.of(
                pusherAuthRoutes,
                accountController,
                loginController,
                matchController,
                playerController,
                teamController,
                tournamentSearchRoutes,
                tournamentCollaboratorController,
                tournamentController,
                phaseController,
                poolController,
                statsController,
                tournamentTossupValueController,
                teamPoolRoutes,
                scoresheetRoutes,
                scheduleRoutes);
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
