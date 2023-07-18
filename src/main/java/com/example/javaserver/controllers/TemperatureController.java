package com.example.javaserver.controllers;

import com.example.javaserver.services.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {

    @Autowired
    private TemperatureService temperatureService;

    @GetMapping("/temperature")
    public String getTemperature() {
        return temperatureService.getTemperature();
    }
}
