package neg5.service.filters;

import static spark.Spark.afterAfter;
import static spark.Spark.before;

import com.google.inject.Inject;
import neg5.userData.CurrentUserContext;
import neg5.userData.UserTokenParser;

public class CurrentUserContextFilter implements RequestFilter {

    @Inject private CurrentUserContext currentUserContext;
    @Inject private UserTokenParser userContextUtil;

    private static final String TOKEN_HEADER = "NEG5_TOKEN";

    @Override
    public void registerFilter() {
        before(
                (request, response) -> {
                    String token = request.headers(TOKEN_HEADER);
                    if (token != null) {
                        currentUserContext.set(userContextUtil.parseToken(token));
                    } else {
                        currentUserContext.set(null);
                    }
                });

        afterAfter((req, res) -> currentUserContext.clear());
    }
}
