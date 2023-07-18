package com.example.javaserver.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TemperatureService {

    public String getTemperature() {
        var response = new RestTemplate().getForEntity(
                "https://api.open-meteo.com/v1/forecast?latitude=45.40&longitude=9.34&current_weather=true",
                String.class);

        return response.getBody();
    }
}
