package neg5.automation.utilities;

public class User {

    private final String nfToken;
    private final String username;

    User(String nfToken, String username) {
        this.nfToken = nfToken;
        this.username = username;
    }

    public String getNfToken() {
        return nfToken;
    }

    public String getUsername() {
        return username;
    }
}
