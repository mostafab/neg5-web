package neg5.domain.api.enums;

public enum StatReportType implements StringIdentifiable {
    TEAM_STANDINGS("team"),
    TEAM_FULL("team_full"),
    INDIVIDUAL("individual"),
    INDIVIDUAL_FULL("individual_full"),
    ROUND_REPORT("round_report");

    private final String id;

    StatReportType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
