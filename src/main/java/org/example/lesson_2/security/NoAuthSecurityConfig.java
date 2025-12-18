package org.example.lesson_2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Extra (+1): no-auth profile for local development.
 *
 * Run with: SPRING_PROFILES_ACTIVE=no-auth
 */
@Configuration
@Profile("no-auth")
public class NoAuthSecurityConfig {

    @Bean
    public SecurityFilterChain noAuthFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
