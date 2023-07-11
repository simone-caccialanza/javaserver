package com.example.javaserver.database.entities;

import com.example.javaserver.domain.DomainEntity;
import com.example.javaserver.domain.PersonDomainEntity;
import com.example.javaserver.responses.IPayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PersonDbEntity extends DbEntity implements DomainEntityMapping{
    
    private String name;
    private Integer age;
    private LocalDate birthDate;

    @Override
    public DomainEntity toDomainEntity() {
        return new PersonDomainEntity(id,name,age,birthDate);
    }
}
