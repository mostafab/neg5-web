package neg5.service.auth;

import static neg5.userData.UserTokenParser.NAME_KEY;
import static neg5.userData.UserTokenParser.USERNAME_KEY;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import neg5.domain.api.AccountApi;
import neg5.domain.api.AccountCreationDTO;
import neg5.domain.api.AccountDTO;
import neg5.domain.api.enums.AccountSource;
import neg5.google.oauth.api.GoogleOauthCredentials;
import neg5.google.oauth.api.GoogleOauthValidator;
import neg5.gson.GsonProvider;
import neg5.jwt.api.JwtApi;
import neg5.jwt.api.JwtData;
import spark.Request;
import spark.Response;

public class LoginAuthenticator {

    private final AccountApi accountManager;
    private final GsonProvider gsonProvider;
    private final JwtApi jwtApi;
    private final GoogleOauthValidator googleOauthValidator;

    @Inject
    public LoginAuthenticator(
            AccountApi accountManager,
            GsonProvider gsonProvider,
            JwtApi jwtApi,
            GoogleOauthValidator googleOauthValidator) {
        this.accountManager = accountManager;
        this.gsonProvider = gsonProvider;
        this.jwtApi = jwtApi;
        this.googleOauthValidator = googleOauthValidator;
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
        Optional<String> decodedPayload =
                googleOauthValidator.validateOauthCredentials(oauthPayload);
        if (!decodedPayload.isPresent()) {
            return false;
        }
        Map<String, String> claims =
                gsonProvider
                        .get()
                        .fromJson(
                                decodedPayload.get(),
                                new TypeToken<Map<String, String>>() {}.getType());
        String emailAddress = Objects.requireNonNull(claims.get("email"));
        Optional<AccountDTO> existingAccountOpt =
                accountManager.findByUsernameOrEmail(emailAddress);
        if (existingAccountOpt.isPresent()) {
            AccountDTO existingAccount = existingAccountOpt.get();
            String token = jwtApi.buildJwt(buildData(existingAccount));
            response.header("NEG5_TOKEN", token);
        } else {
            String name = claims.get("name");
            AccountCreationDTO newAccount = new AccountCreationDTO();
            newAccount.setUsername(emailAddress);
            newAccount.setEmail(emailAddress);
            newAccount.setName(name);
            newAccount.setSource(AccountSource.GOOGLE);
            AccountDTO created = accountManager.createAccount(newAccount);
            String token = jwtApi.buildJwt(buildData(created));
            response.header("NEG5_TOKEN", token);
        }
        return true;
    }

    private JwtData buildData(AccountApi.AccountWithHashedPassword account) {
        return JwtData.newData()
                .put(USERNAME_KEY, account.getUsername())
                .put(NAME_KEY, account.getName());
    }

    private JwtData buildData(AccountDTO account) {
        return JwtData.newData()
                .put(USERNAME_KEY, account.getId())
                .put(NAME_KEY, account.getName());
    }
}
