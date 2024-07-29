package com.example.javaserver.configurations;

import com.example.javaserver.entrypoints.ProblemDetailsAuthenticationEntryPoint;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class SecurityConfiguration {

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(loadSecretKey()).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    SecurityFilterChain appSecurity(HttpSecurity http,
                                    ProblemDetailsAuthenticationEntryPoint entryPoint) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/health/**").authenticated()
                        .anyRequest().authenticated() // Optional: Protects all other endpoints
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .authenticationEntryPoint(entryPoint)
                        .jwt(jwt -> jwt.decoder(jwtDecoder()))
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    private byte[] loadFile(String location) {
        Path path = Paths.get(location);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new byte[0];
        }
    }

    private SecretKey loadSecretKey() {
        byte[] bytesKey = loadFile("hs256_secret");
        return Keys.hmacShaKeyFor(bytesKey);
    }
}
