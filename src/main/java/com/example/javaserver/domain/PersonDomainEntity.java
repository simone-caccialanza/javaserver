package com.example.javaserver.domain;

import com.example.javaserver.database.entities.PersonDbEntity;
import com.example.javaserver.responses.Payload;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonDomainEntity extends DomainEntity implements Payload, DbEntityMapping<PersonDbEntity> {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("birthDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Override
    public PersonDbEntity toDbEntity() {
        return new PersonDbEntity(name, age, birthDate);
    }
}
