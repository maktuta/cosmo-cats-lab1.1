package org.example.lesson_2.cosmocats;

import org.example.lesson_2.feature.FeatureNotAvailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "feature.cosmoCats.enabled=false")
class CosmoCatServiceFeatureDisabledTest {

    @Autowired
    private CosmoCatService service;

    @Test
    void getCosmoCats_throws_when_feature_disabled() {
        assertThrows(FeatureNotAvailableException.class, service::getCosmoCats);
    }
}
