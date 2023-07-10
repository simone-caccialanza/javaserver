package com.example.javaserver.services;

import com.example.javaserver.database.entities.Person;
import com.example.javaserver.database.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonRepositoryService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonRepositoryService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Optional<Person> retrievePerson(UUID id){
        return personRepository.findById(id);
    }

//    public Person update(Person person){
//        Person personToUpdate = personRepository.getReferenceById(person.getId());
//        personToUpdate.;
//    }
}
