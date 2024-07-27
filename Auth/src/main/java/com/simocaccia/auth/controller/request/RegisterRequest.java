package com.simocaccia.auth.controller.request;

public record RegisterRequest(
        String username,
        String password,
        String email
) {
}
