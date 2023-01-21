package neg5.google.oauth.impl;

import neg5.google.oauth.api.DiscoveryDocument;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleOpenIdClient {

    // https://accounts.google.com/.well-known/openid-configuration
    @GET("/.well-known/openid-configuration")
    Call<DiscoveryDocument> getDiscoveryDocument();
}
