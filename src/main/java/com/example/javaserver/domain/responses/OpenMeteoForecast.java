package com.example.javaserver.domain.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoForecast(
        @JsonProperty("latitude") Integer latitude,
        @JsonProperty("longitude") Integer longitude,
        @JsonProperty("generationtime_ms") Float generationtime_ms,
        @JsonProperty("utc_offset_seconds") Integer utc_offset_seconds,
        @JsonProperty("timezone") String timezone,
        @JsonProperty("timezone_abbreviation") String timezone_abbreviation,
        @JsonProperty("elevation") Integer elevation,
        @JsonProperty("current_weather") OpenMeteoCurrentWeather current_weather
) {
}
