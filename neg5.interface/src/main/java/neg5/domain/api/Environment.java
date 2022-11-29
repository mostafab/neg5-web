package neg5.domain.api;

import java.util.Objects;

public enum Environment {
    DEV("dev"),
    STAGING("staging"),
    PRODUCTION("prod");

    private final String label;

    private static final Environment ENVIRONMENT = initializeEnvironment();

    Environment(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }

    private static Environment initializeEnvironment() {
        String env = Objects.requireNonNull(System.getenv("NEG5_ENVIRONMENT"), "NEG5_ENVIRONMENT variable is not set.");
        return Environment.valueOf(env);
    }
}
