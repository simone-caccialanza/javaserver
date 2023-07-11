package com.example.javaserver.domain;

import com.example.javaserver.database.entities.PersonDbEntity;
import com.example.javaserver.responses.IPayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Payload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class PersonDomainEntity extends DomainEntity implements IPayload, DbEntityMapping<PersonDbEntity> {
    @JsonProperty
    private UUID id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer age;
    @JsonProperty
    private LocalDate birthDate;

    @Override
    public PersonDbEntity toDbEntity() {
        return new PersonDbEntity(name,age,birthDate);
    }
}
