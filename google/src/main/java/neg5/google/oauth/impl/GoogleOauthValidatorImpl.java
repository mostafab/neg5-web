package neg5.google.oauth.impl;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
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
    private final GoogleOpenIdClient openIdClient;
    private final Gson gson;
    private final Supplier<DiscoveryDocument> discoveryDocumentCache;
    private final Supplier<Map<String, Object>> certificatesCache;

    @Inject
    public GoogleOauthValidatorImpl(JwtApi jwtApi, GoogleOpenIdClient openIdClient, Gson gson) {
        this.jwtApi = jwtApi;
        this.openIdClient = openIdClient;
        this.gson = gson;

        this.discoveryDocumentCache =
                Suppliers.memoizeWithExpiration(this::getDiscoveryDocument, 24, TimeUnit.HOURS);
        this.certificatesCache = Suppliers.memoizeWithExpiration(this::getCerts, 2, TimeUnit.HOURS);
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
        Map<String, Object> certificates = certificatesCache.get();
        return true;
    }

    private Map<String, Object> getCerts() {
        DiscoveryDocument discoveryDocument = discoveryDocumentCache.get();
        Objects.requireNonNull(
                discoveryDocument.getJwksUri(), "Discovery document does not have jwks_uri");
        Call<Map<String, Object>> call = openIdClient.getJWKS(discoveryDocument.getJwksUri());
        try {
            return call.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DiscoveryDocument getDiscoveryDocument() {
        try {
            Call<DiscoveryDocument> call = openIdClient.getDiscoveryDocument();
            return call.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
