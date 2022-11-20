package org.neg5.jwt.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.neg5.jwt.JwtManager;
import org.neg5.jwt.SigningKeyBackedJwtManagerImpl;

public class JwtSigningModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JwtManager.class).to(SigningKeyBackedJwtManagerImpl.class)
                .in(Scopes.SINGLETON);
    }
}
