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
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import neg5.google.oauth.api.DiscoveryDocument;
import neg5.google.oauth.api.GoogleOauthCredentials;
import neg5.google.oauth.api.GoogleOauthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;

@Singleton
public class GoogleOauthValidatorImpl implements GoogleOauthValidator {
    private final GoogleOpenIdClient openIdClient;
    private final Supplier<DiscoveryDocument> discoveryDocumentCache;
    private static final Set<String> VALID_ISSUERS =
            Sets.newHashSet("https://accounts.google.com", "accounts.google.com");
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleOauthValidatorImpl.class);

    @Inject
    public GoogleOauthValidatorImpl(GoogleOpenIdClient openIdClient) {
        this.openIdClient = openIdClient;
        this.discoveryDocumentCache =
                Suppliers.memoizeWithExpiration(this::getDiscoveryDocument, 24, TimeUnit.HOURS);
    }

    @Override
    public Optional<String> validateOauthCredentials(GoogleOauthCredentials oauthResponse) {
        final DecodedJWT decodedJWT = JWT.decode(oauthResponse.getCredential());
        if (!VALID_ISSUERS.contains(decodedJWT.getIssuer())
                || Instant.now().isAfter(decodedJWT.getExpiresAtAsInstant())) {
            return Optional.empty();
        }
        if (!verifySignature(decodedJWT)) {
            return Optional.empty();
        }
        byte[] decodedPayload = Base64.getDecoder().decode(decodedJWT.getPayload());
        return Optional.of(new String(decodedPayload));
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
        } catch (JwkException e) {
            LOGGER.error("Encountered exception validating signature", e);
            return false;
        } catch (MalformedURLException e) {
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
