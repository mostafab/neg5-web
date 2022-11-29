package neg5.jwt.impl;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import java.util.Base64;

public class JwtParserProvider {

    private final String signingKey;

    public JwtParserProvider(String base64Secret) {
        if (base64Secret == null) {
            throw new IllegalArgumentException("signingKey cannot be null");
        }
        this.signingKey = Base64.getEncoder().encodeToString(base64Secret.getBytes());
    }

    public JwtParser getParser() {
        return Jwts.parser()
                .setSigningKey(signingKey);
    }

    public String getSigningKey() {
        return signingKey;
    }
}
