package org.example.lesson_2.integration;

import org.example.lesson_2.persistence.entity.UserEntity;
import org.example.lesson_2.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

class UserUniqueEmailIT extends PostgresTcBase {

    @Autowired
    private UserRepository userRepository;

    @Test
    void emailIsUnique_enforcedByConstraint() {
        userRepository.saveAndFlush(new UserEntity(null, "unique@cosmo.cats", "First Cat"));
        assertThrows(DataIntegrityViolationException.class, () ->
                userRepository.saveAndFlush(new UserEntity(null, "unique@cosmo.cats", "Second Cat"))
        );
    }
}
