package com.simocaccia.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    void registerUser(String username, String password, String email);
}
