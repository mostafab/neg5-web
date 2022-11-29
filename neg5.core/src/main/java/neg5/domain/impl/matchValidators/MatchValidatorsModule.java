package neg5.domain.impl.matchValidators;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

public class MatchValidatorsModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<EnhancedMatchValidator> multibinder = Multibinder.newSetBinder(binder(), EnhancedMatchValidator.class);
        addBinding(multibinder, TotalTossupsValidator.class);
        addBinding(multibinder, SingleMatchPerRoundValidator.class);
    }

    private void addBinding(Multibinder<EnhancedMatchValidator> multibinder, Class<? extends EnhancedMatchValidator> clazz) {
        multibinder.addBinding().to(clazz).in(Scopes.SINGLETON);
    }
}