package neg5.google.oauth.impl;

import java.util.Map;
import neg5.google.oauth.api.DiscoveryDocument;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GoogleOpenIdClient {

    @GET("/.well-known/openid-configuration")
    Call<DiscoveryDocument> getDiscoveryDocument();

    @GET
    Call<Map<String, Object>> getJWKS(@Url String url);
}
