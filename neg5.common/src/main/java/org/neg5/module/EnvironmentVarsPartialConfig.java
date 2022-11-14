package org.neg5.module;

import com.google.inject.Singleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public final class EnvironmentVarsPartialConfig implements PartialConfiguration {

    private final Map<String, String> properties;

    public EnvironmentVarsPartialConfig() {
        this.properties = Collections.unmodifiableMap(new HashMap<>(System.getenv()));
    }

    @Override
    public Map<String, String> getConfigMap() {
        return properties;
    }
}
