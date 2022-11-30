package neg5.service.filters;

import static spark.Spark.afterAfter;
import static spark.Spark.before;

import com.google.inject.Inject;
import neg5.service.auth.Neg5TokenCookieNameProvider;
import neg5.userData.CurrentUserContext;
import neg5.userData.UserTokenParser;

public class CurrentUserContextFilter implements RequestFilter {

    @Inject private CurrentUserContext currentUserContext;
    @Inject private UserTokenParser userContextUtil;
    @Inject private Neg5TokenCookieNameProvider cookieNameSupplier;

    @Override
    public void registerFilter() {
        before(
                (request, response) -> {
                    String token = request.cookie(cookieNameSupplier.get());
                    if (token != null) {
                        currentUserContext.set(userContextUtil.parseToken(token));
                    } else {
                        currentUserContext.set(null);
                    }
                });

        afterAfter((req, res) -> currentUserContext.clear());
    }
}
