package neg5.domain.api.enums;

public enum ScheduledMatchStatus implements StringIdentifiable {
    SCHEDULED("scheduled"),
    COMPLETE("complete");

    private final String id;

    ScheduledMatchStatus(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
