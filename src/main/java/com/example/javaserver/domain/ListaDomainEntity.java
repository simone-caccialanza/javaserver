package com.example.javaserver.domain;

import com.example.javaserver.database.entities.ListaDbEntity;
import com.example.javaserver.responses.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListaDomainEntity extends DomainEntity implements Payload, DbEntityMapping<ListaDbEntity> {
    @NotNull(groups = PatchEndpoint.class)
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("items")
    private List<ListaItemDomainEntity> items;

    public ListaDomainEntity(UUID id, List<ListaItemDomainEntity> items) {
        this.id = id;
        items.forEach(item -> item.setListaId(id));
        this.items = items;
    }

    @Override
    public ListaDbEntity toDbEntity() {
        return new ListaDbEntity(id, items.stream().map(ListaItemDomainEntity::toDbEntity).toList());
    }

    public interface PatchEndpoint {
    }
}
