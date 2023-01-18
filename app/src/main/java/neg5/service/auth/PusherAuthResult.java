package neg5.service.auth;

import com.google.gson.annotations.SerializedName;

public class PusherAuthResult {

    private String auth;

    @SerializedName("channel_data")
    private String channelData;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getChannelData() {
        return channelData;
    }

    public void setChannelData(String channelData) {
        this.channelData = channelData;
    }
}
