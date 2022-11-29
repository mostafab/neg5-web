package neg5.jwt.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import neg5.jwt.api.JwtApi;
import neg5.jwt.api.JwtData;
import neg5.jwt.module.JwtSigningModule;

import java.util.Date;
import java.util.HashMap;

@Singleton
public class SigningKeyBackedJwtManagerImpl implements JwtApi {

    private final String signingKey;
    private final String appName;
    private final JwtParser jwtParser;

    @Inject
    protected SigningKeyBackedJwtManagerImpl(@Named(JwtSigningModule.BASE_64_ENCODED_STRING_KEY) String signingKey,
                                             @Named("appName") String appName,
                                             JwtParser jwtParser) {
        this.appName = appName;
        this.signingKey = signingKey;
        this.jwtParser = jwtParser;
    }

    @Override
    public String buildJwt(JwtData data) {
        JwtBuilder builder = Jwts
                .builder()
                .setIssuedAt(new Date())
                .setIssuer(appName)
                .signWith(SignatureAlgorithm.HS256, signingKey);
        data.getClaims().forEach(builder::claim);
        return builder.compact();
    }

    @Override
    public JwtData readJwt(String token) {
        Jws<Claims> data = jwtParser.parseClaimsJws(token);
        return new JwtData(new HashMap<>(data.getBody()));
    }
}
