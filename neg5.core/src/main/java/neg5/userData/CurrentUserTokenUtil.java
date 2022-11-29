package neg5.userData;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.neg5.jwt.JwtManager;
import org.neg5.jwt.JwtData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class CurrentUserTokenUtil {

    @Inject private JwtManager jwtManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserTokenUtil.class);

    public UserData getUserData(String token) {
        try {
            JwtData jwtData = jwtManager.readJwt(token);
            return new UserData(jwtData.getClaim("username", String.class));
        } catch (Exception e) {
            LOGGER.error("Encountered exception attempting to get user data", e);
            throw e;
        }
    }
}
