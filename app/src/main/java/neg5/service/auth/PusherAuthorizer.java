package neg5.service.auth;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import neg5.gson.GsonProvider;
import neg5.userData.CurrentUserContext;
import neg5.userData.UserData;
import org.apache.commons.codec.digest.HmacUtils;
import spark.Request;

@Singleton
public class PusherAuthorizer {

    private final CurrentUserContext currentUserContext;
    private final Gson gson;
    private final String pusherSecretKey;
    private final String pusherAppKey;

    @Inject
    public PusherAuthorizer(
            CurrentUserContext currentUserContext,
            GsonProvider gsonProvider,
            @Named("pusher.secretKey") String pusherSecretKey,
            @Named("pusher.appKey") String pusherAppKey) {
        this.currentUserContext = currentUserContext;
        this.gson = gsonProvider.get();
        this.pusherSecretKey = pusherSecretKey;
        this.pusherAppKey = pusherAppKey;
    }

    /**
     * Authorize a
     *
     * @param request
     * @return
     */
    public Optional<PusherAuthResult> authenticate(Request request) {
        if (!currentUserContext.getUserData().isPresent()) {
            return Optional.empty();
        }
        UserData userData = currentUserContext.getUserDataOrThrow();
        String userDataJson = gson.toJson(userData);
        Map<String, Object> channelData = new HashMap<>();
        channelData.put("user_id", userData.getUsername());
        String channelDataJson = gson.toJson(channelData);
        return Optional.ofNullable(getSocketAndChannel(request))
                .map(
                        socketAndChannel ->
                                String.format(
                                        "%s:%s:%s",
                                        socketAndChannel[0], socketAndChannel[1], channelDataJson))
                .map(
                        stringToSign ->
                                new HmacUtils("HmacSHA256", pusherSecretKey).hmacHex(stringToSign))
                .map(
                        signature -> {
                            PusherAuthResult result = new PusherAuthResult();
                            result.setAuth(String.format("%s:%s", pusherAppKey, signature));
                            result.setChannelData(channelDataJson);
                            return result;
                        });
    }

    private String[] getSocketAndChannel(Request request) {
        String[] body = request.body().split("&");
        String[] res = new String[2];
        for (String part : body) {
            String[] values = part.split("=");
            if (values.length != 2) {
                continue;
            }
            if (values[0].equals("socket_id")) {
                res[0] = values[1];
            } else if (values[0].equals("channel_name")) {
                res[1] = values[1];
            }
        }
        if (res[0] == null) {
            return null;
        }
        return res;
    }
}
