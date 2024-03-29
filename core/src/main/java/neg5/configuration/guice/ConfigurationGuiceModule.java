package neg5.configuration.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;
import neg5.domain.api.Environment;

public class ConfigurationGuiceModule extends AbstractModule {

    private static final Pattern ENV_VAR_REPLACE_PATTERN =
            Pattern.compile("\\$\\{(.*?)\\}", Pattern.CASE_INSENSITIVE);

    @Override
    protected void configure() {
        final Environment environment = getEnvironment();
        Names.bindProperties(binder(), getComposedProperties(environment));
        bind(Environment.class).toInstance(environment);
    }

    private Properties getComposedProperties(Environment environment) {
        try {
            final Properties props = new Properties();
            try (InputStream globalConfig =
                            getClass().getClassLoader().getResourceAsStream("config.properties");
                    InputStream environmentConfig =
                            getClass()
                                    .getClassLoader()
                                    .getResourceAsStream(getEnvConfigPath(environment))) {
                // Load generic configs first
                props.load(globalConfig);
                // Override with environment specific configs
                props.load(environmentConfig);
            }
            // Substitute environment vars
            props.putAll(substituteEnvironmentVariables(props));
            return props;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getEnvConfigPath(Environment environment) {
        return String.format("config.%s.properties", environment.getLabel());
    }

    private Map<?, ?> substituteEnvironmentVariables(Properties properties) {
        Map<String, String> overrides = new HashMap<>();
        properties.forEach(
                (key, value) -> {
                    if (shouldSubstituteWithEnvironmentVariable((String) value)) {
                        overrides.put((String) key, getValueOfEnvironmentVar((String) value));
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
        return Objects.requireNonNull(
                System.getenv(envVarKey),
                "No environment variable with key: " + envVarKey + " found.");
    }

    private static Environment getEnvironment() {
        String env =
                Objects.requireNonNull(
                        System.getenv("NEG5_ENVIRONMENT"), "NEG5_ENVIRONMENT variable is not set.");
        return Environment.valueOf(env);
    }
}
