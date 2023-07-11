package com.example.javaserver.controllers;

import com.example.javaserver.database.entities.Person;
import com.example.javaserver.responses.Error;
import com.example.javaserver.responses.ResponseBody;
import com.example.javaserver.services.PersonRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/database")
public class DatabaseController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    @Autowired
    private PersonRepositoryService repositoryService;

    @GetMapping(
            value = "/person/{id}",
            produces = "application/json"
    )
    public ResponseEntity<ResponseBody> getPerson(
            @PathVariable(value = "id") UUID personId
    ) {
        try {
            Optional<Person> dbResult = repositoryService.get(personId);
            return dbResult.map(person ->
                            new ResponseEntity<>(new ResponseBody(ResponseBody.Status.OK, person), HttpStatus.OK))
                    .orElseGet(() ->
                            new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO, List.of(new Error("entity not found"))),
                                    HttpStatus.OK));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO)
                    .addError(new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(
            value = "/person",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<ResponseBody> postPerson(
            @Valid @RequestBody DatabaseRequestBody body
    ) {
        Person dbResult;
        try {
            dbResult = repositoryService.save(body.person);
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.OK, dbResult), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO)
                    .addError(new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private record DatabaseRequestBody(Person person) {
    }
}
