package com.simocaccia.auth.controller;

import com.simocaccia.auth.command.LoginCommand;
import com.simocaccia.auth.command.RefreshCommand;
import com.simocaccia.auth.controller.request.LoginRequest;
import com.simocaccia.auth.controller.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController {


    private final LoginCommand loginCommand;
    private final RefreshCommand refreshCommand;


    @Autowired
    public AuthController(LoginCommand loginCommand, RefreshCommand refreshCommand) {
        this.loginCommand = loginCommand;
        this.refreshCommand = refreshCommand;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        if (request == null || request.authorities() == null || request.authorities().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String jwtToken = loginCommand.run(request, token);

        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @GetMapping(
            path = "/logout"
    )
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/refresh"
    )
    public ResponseEntity<String> refresh(
            @RequestHeader("Authorization") String token
    ) {
        String jwtToken = refreshCommand.run(token);
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping(
            path = "/register"
    )
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/me"
    )
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/token"
    )
    public ResponseEntity<String> token() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/validate"
    )
    public ResponseEntity<String> validate() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/revoke"
    )
    public ResponseEntity<String> revoke() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/status"
    )
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/info"
    )
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/logoutAll"
    )
    public ResponseEntity<String> logoutAll() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/changePassword"
    )
    public ResponseEntity<String> changePassword() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/forgotPassword"
    )
    public ResponseEntity<String> forgotPassword() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resetPassword"
    )
    public ResponseEntity<String> resetPassword() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/changeEmail"
    )
    public ResponseEntity<String> changeEmail() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/forgotEmail"
    )
    public ResponseEntity<String> forgotEmail() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resetEmail"
    )
    public ResponseEntity<String> resetEmail() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/confirmEmail"
    )
    public ResponseEntity<String> confirmEmail() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendEmail"
    )
    public ResponseEntity<String> resendEmail() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/confirmRegistration"
    )
    public ResponseEntity<String> confirmRegistration() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendRegistration"
    )
    public ResponseEntity<String> resendRegistration() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendConfirmation"
    )
    public ResponseEntity<String> resendConfirmation() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendForgotPassword"
    )
    public ResponseEntity<String> resendForgotPassword() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendForgotEmail"
    )
    public ResponseEntity<String> resendForgotEmail() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendResetPassword"
    )
    public ResponseEntity<String> resendResetPassword() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendResetEmail"
    )
    public ResponseEntity<String> resendResetEmail() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendChangePassword"
    )
    public ResponseEntity<String> resendChangePassword() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(
            path = "/resendChangeEmail"
    )
    public ResponseEntity<String> resendChangeEmail() {
        return ResponseEntity.ok("OK");
    }

}

