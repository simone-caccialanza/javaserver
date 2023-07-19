package com.example.javaserver.services;

import com.example.javaserver.gateway.OpenMeteoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TemperatureService {

    @Autowired
    OpenMeteoGateway gateway;

    public String getTemperature() {
        var response = gateway.getForecast(45.40, 9.34);
        return Objects.requireNonNull(response.getBody()).current_weather().temperature().toString();
    }
}
