package com.example.javaserver.database.entities;

import com.example.javaserver.responses.IPayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Person implements IPayload {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty
    @Getter
    private UUID id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer age;
    @JsonProperty
    private LocalDate birthDate;

}
