package org.example.lesson_2.feature;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "feature")
public class FeatureToggleProperties {

    /**
     * Key example: "cosmoCats", "kittyProducts"
     * Value contains 'enabled' flag.
     */
    private Map<String, FeatureFlag> flags = new HashMap<>();

    public Map<String, FeatureFlag> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, FeatureFlag> flags) {
        this.flags = flags;
    }

    public static class FeatureFlag {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
