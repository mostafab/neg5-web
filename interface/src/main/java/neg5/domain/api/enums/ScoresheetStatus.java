package neg5.domain.api.enums;

public enum ScoresheetStatus implements StringIdentifiable {
    DRAFT("draft"),
    SUBMITTED("submitted");

    private final String id;

    ScoresheetStatus(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
