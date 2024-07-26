package com.simocaccia.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Slf4j
@Service
@Getter
public class JwtTokenProviderImpl implements JwtTokenProvider {


    @Value("${custom.security.jwt.expiration-time}")
    private long expirationTime = 100000;
    @Value("${custom.security.private-key-location}")
    private String secretKeyLocation;


    @Override
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = new User(authentication.getPrincipal().toString(), authentication.getCredentials().toString(), authentication.getAuthorities());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        // Create JWT claims
        Claims claims = Jwts.claims()
                .subject(userDetails.getUsername())
                .add("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();

        byte[] bytesKey = loadFile("hs256_secret");
        SecretKey secretKey = Keys.hmacShaKeyFor(bytesKey);

        // Build the JWT token
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
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
}
