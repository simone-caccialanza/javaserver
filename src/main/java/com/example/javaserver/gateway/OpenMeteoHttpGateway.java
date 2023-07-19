package com.example.javaserver.gateway;

import com.example.javaserver.configurations.OpenMeteoGatewayConfiguration;
import com.example.javaserver.domain.responses.OpenMeteoForecast;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenMeteoHttpGateway implements OpenMeteoGateway {


    @Autowired
    private OpenMeteoGatewayConfiguration config;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<OpenMeteoForecast> getForecast(double latitude, double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                .path(config.getForecast())
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("current_weather", true)
                .toUriString();
        return restTemplate.getForEntity(url, OpenMeteoForecast.class);
    }
}
