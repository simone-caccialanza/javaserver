package com.example.javaserver.domain;

import com.example.javaserver.database.entities.ListaDbEntity;
import com.example.javaserver.responses.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ListaDomainEntity extends DomainEntity implements Payload, DbEntityMapping<ListaDbEntity> {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("items")
    private List<ListaItemDomainEntity> items;

    @Override
    public ListaDbEntity toDbEntity() {
        return new ListaDbEntity(id, items.stream().map(ListaItemDomainEntity::toDbEntity).toList());
    }
}
