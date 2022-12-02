package neg5.domain.api;

public enum Environment {
    DEV("dev"),
    CI("ci"),
    STAGING("staging"),
    PRODUCTION("prod");

    private final String label;

    Environment(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
