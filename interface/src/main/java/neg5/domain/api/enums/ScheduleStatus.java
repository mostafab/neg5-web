package neg5.domain.api.enums;

public enum ScheduleStatus implements StringIdentifiable {
    DRAFT("draft"),
    LIVE("live");

    private final String id;

    ScheduleStatus(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
