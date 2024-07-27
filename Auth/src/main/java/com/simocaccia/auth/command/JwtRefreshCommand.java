package com.simocaccia.auth.command;

import com.simocaccia.auth.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtRefreshCommand implements RefreshCommand {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtRefreshCommand(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String run(String token) {
        jwtTokenProvider.validateBearerToken(token);
        return jwtTokenProvider.refreshToken(token);
    }
}
