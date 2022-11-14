package org.neg5.module;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComposedConfiguration implements Configuration {

    private final Map<String, String> properties;

    public ComposedConfiguration(PartialConfiguration ...partialConfigurations) {
        this.properties = new HashMap<>();

        Arrays.stream(partialConfigurations).forEach(config -> {

            this.properties.putAll(config.getConfigMap());
        });
    }

    @Override
    public String getString(String key) {
        return properties.get(key);
    }

    @Override
    public Integer getInt(String key) {
        if (!properties.containsKey(key)) {
            return null;
        }
        return Integer.parseInt(properties.get(key));
    }
}
