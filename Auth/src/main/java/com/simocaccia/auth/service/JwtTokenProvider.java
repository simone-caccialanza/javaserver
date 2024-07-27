package com.simocaccia.auth.service;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface JwtTokenProvider {
    String generateToken(Authentication auithentication);

    void validateBearerToken(String token) throws JwtException;

    String refreshToken(String token);
}
