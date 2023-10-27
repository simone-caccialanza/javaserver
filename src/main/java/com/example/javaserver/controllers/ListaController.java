package com.example.javaserver.controllers;

import com.example.javaserver.domain.ListaDomainEntity;
import com.example.javaserver.responses.Error;
import com.example.javaserver.responses.ResponseBody;
import com.example.javaserver.services.ListaService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "*")
public class ListaController {

    private static final Logger logger = LoggerFactory.getLogger(ListaController.class);

    @Autowired
    private ListaService listaService;

    @GetMapping("/items")
    public ResponseEntity<ResponseBody> getItems(@RequestParam("listaId") UUID listaId) {
        try {
            logger.info("Received request to retrieve a Lista with itemId {}", listaId);
            Optional<ListaDomainEntity> serviceResult = listaService.get(listaId);
            return serviceResult.map(result -> {
                        logger.debug("Retrieved Lista from db: {}", result);
                        return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.OK, result), HttpStatus.OK);
                    })
                    .orElseGet(() ->
                            new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO, List.of(new Error("entity not found"))),
                                    HttpStatus.OK));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO)
                    .addError(new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<ResponseBody> saveItems(@Valid @RequestBody ListaRequestBody body) {
        logger.info("Received request to add a new Lista");
        logger.debug("{}", body);

        try {
            val result = listaService.save(body.listaDomainEntity);
            logger.info("Saved Lista into db with listaId: {}", result.getId());
            logger.debug("{}", result);
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.OK, result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO)
                    .addError(new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/items")
    public ResponseEntity<ResponseBody> updateItems(
            @Validated(ListaDomainEntity.NotNullId.class) @RequestBody ListaRequestBody body) {
        logger.info("Received request to update a Lista with listaId: {}", body.listaDomainEntity.getId());
        logger.debug("{}", body);

        try {
            val result = listaService.saveItems(body.listaDomainEntity);
            logger.info("Saved {} new items into db to listaId {}", result.getItems().size(), body.listaDomainEntity.getId());
            logger.debug("{}", result);
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.OK, result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO)
                    .addError(new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/items")
    public ResponseEntity<ResponseBody> deleteItems(
            @Validated(ListaDomainEntity.NotNullId.class) @RequestBody ListaRequestBody body,
            @Valid @NotNull @RequestParam @Pattern(regexp = UUID_REGEX) UUID listaId) {
        logger.info("Received request to delete items from a Lista with listaId: {}", listaId);
        logger.debug("{}", body);

        try {
            listaService.deleteItems(body.listaDomainEntity);
            logger.info("Deleted {} items from db", body.listaDomainEntity.getItems().size());
            logger.debug("{}", body.listaDomainEntity.getItems());
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.OK), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(ResponseBody.Status.KO)
                    .addError(new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private record ListaRequestBody(@Valid @NotNull @JsonProperty("lista") ListaDomainEntity listaDomainEntity) {
    }


    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
}