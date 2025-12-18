package org.example.lesson_2.cosmocats;

import org.example.lesson_2.feature.FeatureToggle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmoCatService {

    @FeatureToggle("cosmoCats")
    public List<String> getCosmoCats() {
        return List.of("Nebula Whiskers", "Captain Purrnova");
    }
}
