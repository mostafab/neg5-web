package neg5.service.auth;

import com.google.inject.Singleton;
import java.util.function.Supplier;

@Singleton
public class Neg5TokenCookieNameProvider implements Supplier<String> {

    private static final String TOKEN_COOKIE_NAME = "nfToken";

    @Override
    public String get() {
        return TOKEN_COOKIE_NAME;
    }
}
