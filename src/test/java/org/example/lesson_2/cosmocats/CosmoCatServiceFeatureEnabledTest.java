package org.example.lesson_2.cosmocats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "feature.cosmoCats.enabled=true")
class CosmoCatServiceFeatureEnabledTest {

    @Autowired
    private CosmoCatService service;

    @Test
    void getCosmoCats_runs_when_feature_enabled() {
        var cats = service.getCosmoCats();
        assertNotNull(cats);
        assertFalse(cats.isEmpty());
    }
}
