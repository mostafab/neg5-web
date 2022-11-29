package neg5.jwt.api;

import java.util.HashMap;
import java.util.Map;

public class JwtData {

    private final Map<String, Object> claimsProperties;

    public JwtData(Map<String, Object> claimsProperties) {
        this.claimsProperties = claimsProperties;
    }

    public Map<String, Object> getClaims() {
        return claimsProperties;
    }

    public <T> T getClaim(String key) {
        return (T) claimsProperties.get(key);
    }

    public JwtData put(String key, Object value) {
        claimsProperties.put(key, value);
        return this;
    }

    public static JwtData newData() {
        return new JwtData(new HashMap<>());
    }
}
