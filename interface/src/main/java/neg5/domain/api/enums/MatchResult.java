package neg5.domain.api.enums;

public enum MatchResult implements StringIdentifiable {
    WIN("W"),
    LOSS("L"),
    TIE("T"),
    FORFEIT("F");

    private String id;

    MatchResult(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
