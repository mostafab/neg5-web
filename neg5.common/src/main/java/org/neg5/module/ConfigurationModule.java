package org.neg5.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.neg5.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

public class ConfigurationModule extends AbstractModule {

    private static final Pattern ENV_VAR_REPLACE_PATTERN =
            Pattern.compile("\\$\\{(.*?)\\}", Pattern.CASE_INSENSITIVE);

    @Override
    protected void configure() {
        Names.bindProperties(binder(), getComposedProperties());
    }

    private Properties getComposedProperties() {
        try {
            Properties props = new Properties();
            // Load generic configs first
            props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            // Override with environment specific configs
            props.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(getEnvConfigPath())));
            // Substitute environment vars
            props.putAll(substituteEnvironmentVariables(props));
            return props;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getEnvConfigPath() {
        return String.format("config.%s.properties", Environment.getEnvironment().getLabel());
    }

    private Map<?, ?> substituteEnvironmentVariables(Properties properties) {
        Map<String, String> overrides = new HashMap<>();
        properties.forEach((key, value) -> {
            if (shouldSubstituteWithEnvironmentVariable((String) value)) {
                overrides.put(
                        (String) key,
                        getValueOfEnvironmentVar((String) value)
                );
            }
        });
        return overrides;
    }

    private boolean shouldSubstituteWithEnvironmentVariable(String value) {
        return ENV_VAR_REPLACE_PATTERN.matcher(value).matches();
    }

    private String getValueOfEnvironmentVar(String value) {
        // Remove the leading "${" and trailing "}"
        String envVarKey = value.substring(2, value.length() - 1);
        return Objects.requireNonNull(System.getenv(envVarKey), "No environment variable with key: " + envVarKey + " found.");
    }

}