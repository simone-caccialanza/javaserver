package com.simocaccia.auth.command;

import com.simocaccia.auth.controller.request.LoginRequest;
import com.simocaccia.auth.service.AuthenticationService;
import com.simocaccia.auth.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class BaseLoginCommand implements LoginCommand {

    private final RefreshCommand refreshCommand;
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public BaseLoginCommand(RefreshCommand refreshCommand, AuthenticationService authenticationService, JwtTokenProvider jwtTokenProvider) {
        this.refreshCommand = refreshCommand;
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String run(LoginRequest loginRequest, String token) {

        if (token == null || token.isEmpty()) {
            Authentication authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

            Authentication authentication = authenticationService.authenticate(authenticationToken);

            return jwtTokenProvider.generateToken(authentication);
        } else {
            return refreshCommand.run(token);
        }
    }

}
