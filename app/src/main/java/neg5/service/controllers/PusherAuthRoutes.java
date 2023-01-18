package neg5.service.controllers;

import com.google.inject.Inject;
import java.util.Optional;
import neg5.service.auth.PusherAuthResult;
import neg5.service.auth.PusherAuthorizer;
import org.eclipse.jetty.http.HttpStatus;

public class PusherAuthRoutes extends AbstractJsonRoutes {

    @Inject private PusherAuthorizer authorizer;

    @Override
    protected String getBasePath() {
        return "/neg5-api/pusher";
    }

    @Override
    public void registerRoutes() {
        post(
                "/auth",
                (req, res) -> {
                    Optional<PusherAuthResult> authResult = authorizer.authenticate(req);
                    if (!authResult.isPresent()) {
                        res.status(HttpStatus.FORBIDDEN_403);
                        return "Invalid request";
                    }
                    return authResult.get();
                });
    }
}
