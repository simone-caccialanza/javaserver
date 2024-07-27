package com.simocaccia.auth.command;

import com.simocaccia.auth.controller.request.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public interface RegisterCommand {
    void run(RegisterRequest registerRequest);
}
