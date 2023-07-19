package com.example.javaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class JavaserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaserverApplication.class, args);
    }

}
