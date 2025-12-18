package org.example.lesson_2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Provides a local JwtDecoder based on a public key stored in classpath.
 *
 * This avoids network calls to an external issuer/jwks endpoint during app startup.
 * The key is located at: src/main/resources/keys/public.pem
 */
@Configuration
public class JwtDecoderConfig {

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        RSAPublicKey publicKey = loadPublicKey();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    private RSAPublicKey loadPublicKey() throws Exception {
        String pem = readClasspathFile("keys/public.pem");
        String normalized = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(normalized);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(keySpec);
    }

    private String readClasspathFile(String path) throws IOException {
        ClassPathResource res = new ClassPathResource(path);
        byte[] bytes = res.getInputStream().readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
