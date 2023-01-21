package neg5.domain.api.enums;

public enum AccountSource implements StringIdentifiable {
    MANUAL("manual"),
    GOOGLE("google");

    private String id;

    AccountSource(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
