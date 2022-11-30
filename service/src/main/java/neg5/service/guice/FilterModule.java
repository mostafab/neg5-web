package neg5.service.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import neg5.service.filters.CurrentUserContextFilter;
import neg5.service.filters.GeneralExceptionHandlerFilter;
import neg5.service.filters.NoResultHandlerFilter;
import neg5.service.filters.ObjectValidationExceptionHandlerFilter;
import neg5.service.filters.RequestFilter;
import neg5.service.filters.TournamentAccessExceptionFilter;

public class FilterModule extends AbstractModule {

    @Override
    protected void configure() {
        final Multibinder<RequestFilter> multibinder =
                Multibinder.newSetBinder(binder(), RequestFilter.class);

        multibinder.addBinding().to(NoResultHandlerFilter.class);
        multibinder.addBinding().to(CurrentUserContextFilter.class);
        multibinder.addBinding().to(TournamentAccessExceptionFilter.class);
        multibinder.addBinding().to(ObjectValidationExceptionHandlerFilter.class);
        multibinder.addBinding().to(GeneralExceptionHandlerFilter.class);
    }
}
