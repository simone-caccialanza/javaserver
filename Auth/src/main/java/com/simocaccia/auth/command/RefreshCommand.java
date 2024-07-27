package com.simocaccia.auth.command;

import org.springframework.stereotype.Component;

@Component
public interface RefreshCommand {
    public String run(String input);
}
