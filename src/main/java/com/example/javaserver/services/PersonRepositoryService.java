package com.example.javaserver.services;

import com.example.javaserver.database.entities.PersonDbEntity;
import com.example.javaserver.database.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonRepositoryService implements RepositoryService<PersonDbEntity,UUID>{

    private final PersonRepository personRepository;

    @Autowired
    public PersonRepositoryService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDbEntity save(PersonDbEntity personDbEntity){
        return personRepository.save(personDbEntity);
    }

    public Optional<PersonDbEntity> get(UUID id){
        return personRepository.findById(id);
    }

    public PersonDbEntity update(@NotNull PersonDbEntity personDbEntity){
        return personRepository.save(personDbEntity);
    }
}
