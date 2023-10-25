package com.example.javaserver.database.entities;

import com.example.javaserver.domain.PersonDomainEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonDbEntity extends DbEntity implements DomainEntityMapping<PersonDomainEntity> {

    private String name;
    private Integer age;
    private LocalDate birthDate;

    @Override
    public PersonDomainEntity toDomainEntity() {
        return new PersonDomainEntity(id, name, age, birthDate);
    }
}
