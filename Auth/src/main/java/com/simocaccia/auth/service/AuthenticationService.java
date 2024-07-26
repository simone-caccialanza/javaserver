package com.simocaccia.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    Authentication authenticate(Authentication authentication);
}
