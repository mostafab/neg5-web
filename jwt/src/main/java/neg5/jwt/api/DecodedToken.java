package neg5.jwt.api;

public class DecodedToken {

    private final String header;
    private final String body;
    private final String signature;

    public DecodedToken(String header, String body, String signature) {
        this.header = header;
        this.body = body;
        this.signature = signature;
    }

    public String getBody() {
        return body;
    }

    public String getHeader() {
        return header;
    }

    public String getSignature() {
        return signature;
    }
}
