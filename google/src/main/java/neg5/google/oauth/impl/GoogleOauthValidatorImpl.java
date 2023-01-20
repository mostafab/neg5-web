package neg5.google.oauth.impl;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import neg5.google.oauth.api.DiscoveryDocument;
import neg5.google.oauth.api.GoogleJwtTokenFields;
import neg5.google.oauth.api.GoogleOauthCredentials;
import neg5.google.oauth.api.GoogleOauthValidator;
import neg5.jwt.api.DecodedToken;
import neg5.jwt.api.JwtApi;
import retrofit2.Call;

@Singleton
public class GoogleOauthValidatorImpl implements GoogleOauthValidator {

    private final JwtApi jwtApi;
    private final GoogleDiscoveryDocumentClient discoveryDocumentClient;
    private final Gson gson;
    private final Supplier<DiscoveryDocument> discoveryDocumentCache;

    @Inject
    public GoogleOauthValidatorImpl(
            JwtApi jwtApi, GoogleDiscoveryDocumentClient discoveryDocumentClient, Gson gson) {
        this.jwtApi = jwtApi;
        this.discoveryDocumentClient = discoveryDocumentClient;
        this.gson = gson;

        this.discoveryDocumentCache =
                Suppliers.memoizeWithExpiration(this::getDiscoveryDocument, 24, TimeUnit.HOURS);
    }

    @Override
    public boolean validateOauthCredentials(GoogleOauthCredentials oauthResponse) {
        DecodedToken tokenData = jwtApi.decodeToken(oauthResponse.getCredential());
        GoogleJwtTokenFields token = gson.fromJson(tokenData.getBody(), GoogleJwtTokenFields.class);
        if (!verifySignature(tokenData)) {
            return false;
        }
        return false;
    }

    private boolean verifySignature(DecodedToken token) {
        String signature = token.getSignature();
        DiscoveryDocument discoveryDocument = discoveryDocumentCache.get();
        return true;
    }

    private DiscoveryDocument getDiscoveryDocument() {
        try {
            Call<DiscoveryDocument> call = discoveryDocumentClient.getDiscoveryDocument();
            return call.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
