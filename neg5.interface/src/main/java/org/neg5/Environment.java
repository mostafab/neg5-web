package org.neg5;

public enum Environment {
    DEV("dev"),
    PRODUCTION("prod");

    private final String label;

    Environment(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Environment getEnvironment() {
        String env = System.getenv("NEG5_ENVIRONMENT");
        return Environment.valueOf(env);
    }
}
