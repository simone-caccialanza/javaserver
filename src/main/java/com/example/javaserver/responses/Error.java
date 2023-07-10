package com.example.javaserver.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
    @JsonProperty
    private final String code;
    @JsonProperty
    private final String description;

    public Error(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Error(String description) {
        this.code = "";
        this.description = description;
    }
}
