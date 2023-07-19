package com.example.javaserver.gateway;

import com.example.javaserver.domain.responses.OpenMeteoForecast;
import org.springframework.http.ResponseEntity;

public interface OpenMeteoGateway {
    ResponseEntity<OpenMeteoForecast> getForecast(double latitude, double longitude);
}
