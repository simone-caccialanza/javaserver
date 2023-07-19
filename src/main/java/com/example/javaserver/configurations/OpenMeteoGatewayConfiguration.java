package com.example.javaserver.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "open-meteo")
@Getter
@Setter
//TODO custom annotation for configuration classes
public class OpenMeteoGatewayConfiguration {
    String baseUrl;
    String forecast;
}
