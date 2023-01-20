package neg5.google.oauth.impl;

import neg5.google.oauth.api.DiscoveryDocument;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleDiscoveryDocumentClient {

    @GET("/.well-known/openid-configuration")
    Call<DiscoveryDocument> getDiscoveryDocument();
}
