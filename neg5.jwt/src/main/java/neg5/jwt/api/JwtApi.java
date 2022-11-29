package neg5.jwt.api;

public interface JwtApi {

    String buildJwt(JwtData data);

    JwtData readJwt(String token);

}
