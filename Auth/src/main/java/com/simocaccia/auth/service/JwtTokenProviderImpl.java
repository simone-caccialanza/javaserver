package com.simocaccia.auth.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

//TODO: add aud and issuer
//TODO: add checks on all claims
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

        SecretKey secretKey = loadSecretKey();

        // Build the JWT token
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }


    @Override
    public void validateBearerToken(@NonNull String token) throws JwtException {
        if (token.isBlank() || token.isEmpty()) {
            throw new InvalidBearerTokenException("Token is empty");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        try {
            JwtParser jwtParser = Jwts.parser()
                    .verifyWith(loadSecretKey())
                    .build();
            log.debug("token |{}|", token);
            Jws<Claims> jws = jwtParser.parseSignedClaims(token);
            Claims claims = jws.getPayload();
            Header header = jws.getHeader();

            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                throw new ExpiredJwtException(header, claims, "Token has expired, try to login");
            }

        } catch (SignatureException e) {
            throw new io.jsonwebtoken.security.SignatureException("Invalid token signature");
        }

    }

    @Override
    public String refreshToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(loadSecretKey())
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        // Create JWT claims
        Claims newClaims = Jwts.claims()
                .subject(claims.getSubject())
                .add("authorities", claims.get("authorities"))
                .build();

        // Build the JWT token
        return Jwts.builder()
                .claims(newClaims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(loadSecretKey())
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

    private SecretKey loadSecretKey() {
        byte[] bytesKey = loadFile("hs256_secret");
        return Keys.hmacShaKeyFor(bytesKey);
    }
}
