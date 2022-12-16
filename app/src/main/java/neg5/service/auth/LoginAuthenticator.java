package neg5.service.auth;

import com.google.inject.Inject;
import java.time.Instant;
import neg5.domain.api.AccountApi;
import neg5.gson.GsonProvider;
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
        if (accountManager.verifyPassword(credentials.getUsername(), credentials.getPassword())) {
            String token = jwtApi.buildJwt(buildData(credentials));
            response.header("NEG5_TOKEN", token);
            return true;
        }
        return false;
    }

    private JwtData buildData(LoginCreds loginCreds) {
        return JwtData.newData()
                .put("username", loginCreds.getUsername())
                .put("issuedAt", Instant.now().toEpochMilli());
    }
}
