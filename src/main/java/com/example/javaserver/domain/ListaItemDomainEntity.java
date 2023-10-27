package com.example.javaserver.domain;

import com.example.javaserver.database.entities.ListaItemDbEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ListaItemDomainEntity extends DomainEntity implements DbEntityMapping<ListaItemDbEntity> {

    private String id;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID listaId;

    @Override
    public ListaItemDbEntity toDbEntity() {
        return new ListaItemDbEntity(id, description, listaId);
    }
}
