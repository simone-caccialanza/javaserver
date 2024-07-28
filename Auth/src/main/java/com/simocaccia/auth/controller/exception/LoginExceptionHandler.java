package com.simocaccia.auth.controller.exception;

import com.simocaccia.auth.controller.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class LoginExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<AuthResponse> handleDisabledException(Exception e, WebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pd.setTitle("Account disabled");
        pd.setDetail(e.getMessage());
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        pd.setInstance(URI.create(requestUri));
//        pd.setInstance(URI.create(request.getContextPath())); //TODO understand how to retrieve path
        return ResponseEntity.status(pd.getStatus()).body(AuthResponse.withError(pd));
    }
}
