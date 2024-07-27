package com.simocaccia.auth.command;

import com.simocaccia.auth.controller.request.LoginRequest;
import org.springframework.stereotype.Component;

@Component
public interface LoginCommand {
    String run(LoginRequest loginRequest, String token);
}
