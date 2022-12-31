package neg5.userData;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.jwt.api.JwtApi;
import neg5.jwt.api.JwtData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class UserTokenParser {

    @Inject private JwtApi jwtApi;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenParser.class);

    public static final String NAME_KEY = "name";
    public static final String USERNAME_KEY = "username";

    public UserData parseToken(String token) {
        try {
            JwtData jwtData = jwtApi.readJwt(token);
            return new UserData(jwtData.getClaim(USERNAME_KEY), jwtData.getClaim(NAME_KEY));
        } catch (Exception e) {
            LOGGER.error("Encountered exception attempting to get user data", e);
            throw e;
        }
    }
}
