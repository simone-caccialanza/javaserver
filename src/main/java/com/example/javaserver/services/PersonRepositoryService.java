package com.example.javaserver.services;

import com.example.javaserver.database.entities.Person;
import com.example.javaserver.database.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonRepositoryService implements RepositoryService<Person,UUID>{

    private final PersonRepository personRepository;

    @Autowired
    public PersonRepositoryService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Optional<Person> get(UUID id){
        return personRepository.findById(id);
    }

    public Person update(@NotNull Person person){
        return personRepository.save(person);
    }
}
