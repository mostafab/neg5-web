package neg5.google.oauth.impl;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import neg5.google.oauth.api.DiscoveryDocument;
import neg5.google.oauth.api.GoogleJwtTokenFields;
import neg5.google.oauth.api.GoogleOauthCredentials;
import neg5.google.oauth.api.GoogleOauthValidator;
import retrofit2.Call;

@Singleton
public class GoogleOauthValidatorImpl implements GoogleOauthValidator {
    private final GoogleOpenIdClient openIdClient;
    private final Gson gson;
    private final Supplier<DiscoveryDocument> discoveryDocumentCache;

    @Inject
    public GoogleOauthValidatorImpl(GoogleOpenIdClient openIdClient, Gson gson) {
        this.openIdClient = openIdClient;
        this.gson = gson;
        this.discoveryDocumentCache =
                Suppliers.memoizeWithExpiration(this::getDiscoveryDocument, 24, TimeUnit.HOURS);
    }

    @Override
    public boolean validateOauthCredentials(GoogleOauthCredentials oauthResponse) {
        final DecodedJWT decodedJWT = JWT.decode(oauthResponse.getCredential());
        if (!verifySignature(decodedJWT)) {
            return false;
        }
        GoogleJwtTokenFields token =
                gson.fromJson(decodedJWT.getPayload(), GoogleJwtTokenFields.class);
        return true;
    }

    private boolean verifySignature(DecodedJWT decodedJWT) {
        final DiscoveryDocument discoveryDocument = discoveryDocumentCache.get();
        try {
            final JwkProvider webKeyProvider =
                    new UrlJwkProvider(new URL(discoveryDocument.getJwksUri()));
            final Jwk webKey = webKeyProvider.get(decodedJWT.getKeyId());
            final Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) webKey.getPublicKey());
            algorithm.verify(decodedJWT);
            return true;
        } catch (JwkException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private DiscoveryDocument getDiscoveryDocument() {
        try {
            Call<DiscoveryDocument> call = openIdClient.getDiscoveryDocument();
            DiscoveryDocument result = call.execute().body();
            Objects.requireNonNull(result);
            Objects.requireNonNull(result.getJwksUri(), "jwks_uri is null.");
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
