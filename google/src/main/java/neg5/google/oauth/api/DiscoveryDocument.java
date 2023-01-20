package neg5.google.oauth.api;

import com.google.gson.annotations.SerializedName;

/**
 * Mimics the stucture of Google OpenID config:
 * https://accounts.google.com/.well-known/openid-configuration
 */
public class DiscoveryDocument {

    @SerializedName("jwks_uri")
    private String jwksUri;

    public String getJwksUri() {
        return jwksUri;
    }

    public void setJwksUri(String jwksUri) {
        this.jwksUri = jwksUri;
    }
}
