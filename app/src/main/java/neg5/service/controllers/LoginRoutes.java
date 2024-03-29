package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.service.auth.LoginAuthenticator;
import org.eclipse.jetty.http.HttpStatus;

public class LoginRoutes extends AbstractRoutes {

    @Inject private LoginAuthenticator loginAuthenticator;

    @Override
    protected String getBasePath() {
        return "/neg5-api/login";
    }

    @Override
    public void registerRoutes() {
        post(
                "",
                (req, res) -> {
                    if (loginAuthenticator.loginByRequest(req, res)) {
                        return "OK";
                    }
                    res.status(HttpStatus.FORBIDDEN_403);
                    return "Invalid";
                });

        post(
                "/google",
                (req, res) -> {
                    if (loginAuthenticator.loginByGoogleCredentials(req, res)) {
                        return "OK";
                    }
                    res.status(HttpStatus.FORBIDDEN_403);
                    return "Invalid";
                });
    }
}
