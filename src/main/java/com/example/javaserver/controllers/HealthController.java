package com.example.javaserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Autowired
    private HealthEndpoint healthEndpoint;

    @GetMapping(
            path = "/health"
    )
    public HealthComponent health() {
        return healthEndpoint.health();
    }
}
