package com.simocaccia.auth.command;

import com.simocaccia.auth.controller.request.RegisterRequest;
import com.simocaccia.auth.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEmailRegisterCommand implements RegisterCommand {

    private final RegistrationService registrationService;

    @Autowired
    public UserEmailRegisterCommand(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void run(RegisterRequest registerRequest) {
        registrationService.registerUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
    }
}
