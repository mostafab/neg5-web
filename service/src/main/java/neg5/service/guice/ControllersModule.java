package neg5.service.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
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

public class ControllersModule extends AbstractModule {

    @Override
    protected void configure() {
        final Multibinder<BaseController> multibinder = Multibinder.newSetBinder(binder(), BaseController.class);

        multibinder.addBinding().to(AccountController.class);
        multibinder.addBinding().to(LoginController.class);
        multibinder.addBinding().to(MatchController.class);
        multibinder.addBinding().to(PlayerController.class);
        multibinder.addBinding().to(TeamController.class);
        multibinder.addBinding().to(TournamentCollaboratorController.class);
        multibinder.addBinding().to(TournamentController.class);
        multibinder.addBinding().to(TournamentPhaseController.class);
        multibinder.addBinding().to(TournamentPoolController.class);
        multibinder.addBinding().to(TournamentStatsController.class);
        multibinder.addBinding().to(TournamentTossupValueController.class);
    }
}
