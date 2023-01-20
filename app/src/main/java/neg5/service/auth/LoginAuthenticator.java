package neg5.service.auth;

import static neg5.userData.UserTokenParser.NAME_KEY;
import static neg5.userData.UserTokenParser.USERNAME_KEY;

import com.google.inject.Inject;
import java.time.Instant;
import java.util.Optional;
import neg5.domain.api.AccountApi;
import neg5.google.oauth.api.GoogleJwtTokenFields;
import neg5.google.oauth.api.GoogleOauthCredentials;
import neg5.gson.GsonProvider;
import neg5.jwt.api.DecodedToken;
import neg5.jwt.api.JwtApi;
import neg5.jwt.api.JwtData;
import spark.Request;
import spark.Response;

public class LoginAuthenticator {

    private final AccountApi accountManager;
    private final GsonProvider gsonProvider;
    private final JwtApi jwtApi;

    @Inject
    public LoginAuthenticator(AccountApi accountManager, GsonProvider gsonProvider, JwtApi jwtApi) {
        this.accountManager = accountManager;
        this.gsonProvider = gsonProvider;
        this.jwtApi = jwtApi;
    }

    public boolean loginByRequest(Request request, Response response) {
        LoginCreds credentials = gsonProvider.get().fromJson(request.body(), LoginCreds.class);
        return loginByCredentials(credentials, response);
    }

    public boolean loginByCredentials(LoginCreds credentials, Response response) {
        Optional<AccountApi.AccountWithHashedPassword> account =
                accountManager.verifyPassword(
                        credentials.getUsernameOrEmail(), credentials.getPassword());
        if (account.isPresent()) {
            String token = jwtApi.buildJwt(buildData(account.get()));
            response.header("NEG5_TOKEN", token);
            return true;
        }
        return false;
    }

    public boolean loginByGoogleCredentials(Request request, Response response) {
        GoogleOauthCredentials oauthPayload =
                gsonProvider.get().fromJson(request.body(), GoogleOauthCredentials.class);
        if (oauthPayload.getCredential() == null) {
            return false;
        }
        DecodedToken tokenData = jwtApi.decodeToken(oauthPayload.getCredential());
        GoogleJwtTokenFields token =
                gsonProvider.get().fromJson(tokenData.getBody(), GoogleJwtTokenFields.class);
        return true;
    }

    private JwtData buildData(AccountApi.AccountWithHashedPassword account) {
        return JwtData.newData()
                .put(USERNAME_KEY, account.getUsername())
                .put(NAME_KEY, account.getName())
                .put("issuedAt", Instant.now().toEpochMilli());
    }
}
