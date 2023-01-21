package neg5.google.oauth.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import neg5.google.oauth.api.GoogleOauthValidator;
import neg5.google.oauth.impl.GoogleOauthValidatorImpl;
import neg5.google.oauth.impl.GoogleOpenIdClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleOauthGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GoogleOauthValidator.class).to(GoogleOauthValidatorImpl.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public GoogleOpenIdClient provideDiscoveryClient(
            @Named("google.discoveryDocument.baseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleOpenIdClient.class);
    }
}
