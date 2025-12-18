package org.example.lesson_2.feature;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {

    private final FeatureToggleService toggleService;

    public FeatureToggleAspect(FeatureToggleService toggleService) {
        this.toggleService = toggleService;
    }

    @Around("@annotation(featureToggle)")
    public Object around(ProceedingJoinPoint pjp, FeatureToggle featureToggle) throws Throwable {
        String key = featureToggle.value();

        if (toggleService.isEnabled(key)) {
            return pjp.proceed();
        }
        throw new FeatureNotAvailableException(key);
    }
}
