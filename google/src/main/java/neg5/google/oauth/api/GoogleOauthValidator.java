package neg5.google.oauth.api;

import java.util.Optional;

public interface GoogleOauthValidator {

    Optional<String> validateOauthCredentials(GoogleOauthCredentials oauthResponse);
}
