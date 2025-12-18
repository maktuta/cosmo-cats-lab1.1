package org.example.lesson_2.feature;

import org.springframework.stereotype.Service;

@Service
public class FeatureToggleService {

    private final FeatureToggleProperties props;

    public FeatureToggleService(FeatureToggleProperties props) {
        this.props = props;
    }

    public boolean isEnabled(String featureKey) {
        var flag = props.getFlags().get(featureKey);
        return flag != null && flag.isEnabled();
    }
}
