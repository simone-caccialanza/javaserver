package com.example.javaserver.domain.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record OpenMeteoCurrentWeather(
        @JsonProperty("temperature") Integer temperature,
        @JsonProperty("windspeed") Integer windspeed,
        @JsonProperty("winddirection") Integer winddirection,
        @JsonProperty("weathercode") Integer weathercode,
        @JsonProperty("is_day") Integer is_day,
        @JsonProperty("time") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime time
) {
}
