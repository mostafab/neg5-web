package neg5.jwt.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.name.Named;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import neg5.jwt.api.JwtApi;
import neg5.jwt.impl.SigningKeyBackedJwtManagerImpl;

import java.util.Base64;

public class JwtSigningModule extends AbstractModule {

    public static final String BASE_64_ENCODED_STRING_KEY = "jwt.secret.base64";

    @Override
    protected void configure() {
        bind(JwtApi.class).to(SigningKeyBackedJwtManagerImpl.class)
                .in(Scopes.SINGLETON);
    }

    @Named(BASE_64_ENCODED_STRING_KEY)
    @Provides
    public String provideBase64EncodedSigningKey(@Named("jwt.secret") String signingKey) {
        return Base64.getEncoder().encodeToString(signingKey.getBytes());
    }

    @Provides
    public JwtParser provideParser(@Named(BASE_64_ENCODED_STRING_KEY) String encodedKey) {
        return Jwts.parser().setSigningKey(encodedKey);
    }
}
