package com.simocaccia.auth.controller.response;

import org.springframework.http.ProblemDetail;

public record AuthResponse(
        String status,
        ProblemDetail error
) {
    public static AuthResponse withError(ProblemDetail problemDetail) {
        return new AuthResponse("error", problemDetail);
    }
}
