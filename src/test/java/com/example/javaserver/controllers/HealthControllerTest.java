package com.example.javaserver.controllers;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class HealthControllerTest {

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldShowAllTokenValidationErrors() throws Exception {
        String expired = mint((claims) -> claims
                .audience(List.of("http://wrongurl"))
                .issuedAt(Instant.now().minusSeconds(3600))
                .expiresAt(Instant.now().minusSeconds(3599))
        );
        this.mvc.perform(get("/health").header("Authorization", "Bearer " + expired))
                .andExpect(status().isUnauthorized())
                .andExpect(header().exists("WWW-Authenticate"))
                .andExpect(jsonPath("$.errors..description").value(
                        containsInAnyOrder(containsString("Jwt expired"), containsString("aud claim is not valid"))));
    }

    @Test
    void shouldRequireValidTokens() throws Exception {
        String token = mint();
        this.mvc.perform(get("/health")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String mint() {
        return mint(consumer -> {
        });
    }

    private String mint(Consumer<JwtClaimsSet.Builder> consumer) {
        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(100000))
                .subject("user")
                .issuer("http://localhost:8080")
                .audience(Arrays.asList("http://localhost:8080"))
                .claim("scp", Arrays.asList("health:read", "health:write"));
        consumer.accept(builder);
        JwtEncoderParameters parameters = JwtEncoderParameters.from(builder.build());
        return this.jwtEncoder.encode(parameters).getTokenValue();
    }


    @TestConfiguration
    static class TestJwtConfiguration {
        @Bean
        JwtEncoder jwtEncoder(@Value("classpath:keys/test_public_key.pem") RSAPublicKey pub,
                              @Value("classpath:keys/test_private_key.pem") RSAPrivateKey pem) {
            RSAKey key = new RSAKey.Builder(pub).privateKey(pem).build();
            return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(key)));
        }
    }

}
