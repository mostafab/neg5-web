package neg5.exports.qbj.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import neg5.exports.qbj.api.QbjApi;
import neg5.exports.qbj.impl.QbjApiImpl;

public class QbjGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(QbjApi.class).to(QbjApiImpl.class).in(Scopes.SINGLETON);
    }
}
