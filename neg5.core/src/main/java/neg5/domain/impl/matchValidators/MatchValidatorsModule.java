package neg5.domain.impl.matchValidators;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

public class MatchValidatorsModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<TournamentMatchValidator> multibinder =
                Multibinder.newSetBinder(binder(), TournamentMatchValidator.class);
        addBinding(multibinder, BasicMatchValidator.class);
        addBinding(multibinder, TotalTossupsValidator.class);
        addBinding(multibinder, SingleMatchPerRoundValidator.class);
    }

    private void addBinding(
            Multibinder<TournamentMatchValidator> multibinder,
            Class<? extends TournamentMatchValidator> clazz) {
        multibinder.addBinding().to(clazz).in(Scopes.SINGLETON);
    }
}
