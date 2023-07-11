package com.example.javaserver.responses;

import com.example.javaserver.database.entities.Person;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ResponseBody {

    @JsonProperty
    private final Status status;
    @JsonProperty
    private final List<Error> errors;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final IPayload payload;

    public ResponseBody(Status status, IPayload payload) {
        this.status = status;
        this.payload = payload;
        this.errors = Collections.emptyList();
    }

    public ResponseBody(Status status, List<Error> errors) {
        this.status = status;
        this.payload = null;
        this.errors = new LinkedList<>(errors);
    }

    public ResponseBody(Status status) {
        this.status = status;
        this.payload = new Person();
        this.errors = new LinkedList<>(); //can be better
    }

    public ResponseBody addError(Error error) {
        this.errors.add(error);
        return this;
    }

    public enum Status {OK,KO}
}