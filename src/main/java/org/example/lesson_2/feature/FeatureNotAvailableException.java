package org.example.lesson_2.feature;

public class FeatureNotAvailableException extends RuntimeException {
    public FeatureNotAvailableException(String featureKey) {
        super("Feature is not available: " + featureKey);
    }
}
