package org.example.lesson_2.feature;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeatureToggle {
    /**
     * Feature key: e.g. "cosmoCats"
     */
    String value();
}
