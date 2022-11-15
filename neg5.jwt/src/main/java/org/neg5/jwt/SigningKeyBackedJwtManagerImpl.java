package org.neg5.jwt;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.jsonwebtoken.*;
import org.neg5.jwt.module.JwtSigningModule;

import java.util.Date;
import java.util.HashMap;

public class SigningKeyBackedJwtManagerImpl implements JwtManager {

    private final JwtParserProvider parserProvider;
    private final JwtParser jwtParser;

    @Inject
    protected SigningKeyBackedJwtManagerImpl(@Named("security.jwt.secret") String signingKey) {
        this.parserProvider = new JwtParserProvider(signingKey);
        jwtParser = this.parserProvider.get();
    }

    @Override
    public String buildJwt(JwtData data) {
        JwtBuilder builder = Jwts
                .builder()
                .setIssuedAt(new Date())
                .setIssuer("Neg5.service")
                .signWith(SignatureAlgorithm.HS256, parserProvider.getSigningKey());
        data.getClaims().forEach(builder::claim);
        return builder.compact();
    }

    @Override
    public JwtData readJwt(String token) {
        Jws<Claims> data = jwtParser.parseClaimsJws(token);
        return new JwtData(new HashMap<>(data.getBody()));
    }
}
