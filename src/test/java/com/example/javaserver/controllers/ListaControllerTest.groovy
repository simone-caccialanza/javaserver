package com.example.javaserver.controllers

import com.example.javaserver.domain.ListaDomainEntity
import com.example.javaserver.services.ListaService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class ListaControllerTest extends Specification {

    @Autowired
    ListaService listaService

    def "getItems should return entity when entity exists"() {
        given:
        def serviceResult = Optional.of(new ListaDomainEntity())
        when:
        def responseEntity = listaController.getItems("anyString")
        then:
        1 * listaService.get("anyString") >> serviceResult
        responseEntity.getStatusCode() == HttpStatus.OK
        responseEntity.getBody().data == serviceResult.get()
    }

}
