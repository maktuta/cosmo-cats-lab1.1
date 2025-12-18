package org.example.lesson_2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private final List<String> cosmicWords = List.of("star", "galaxy", "comet", "planet", "moon", "asteroid");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull already checks null
        String lower = value.toLowerCase();
        return cosmicWords.stream().anyMatch(lower::contains);
    }
}
