package com.example.javaserver.mappers;

import com.example.javaserver.database.entities.ListaItemDbEntity;
import com.example.javaserver.domain.ListaItemDomainEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ListaItemMapper implements DomainDbMapper<ListaItemDomainEntity, ListaItemDbEntity> {
    @Override
    public ListaItemDomainEntity mapToDomainEntity(ListaItemDbEntity entity) {
        return new ListaItemDomainEntity(entity.getId(), entity.getDescription(), entity.getListaId(), entity.getChecked());
    }

    @Override
    public ListaItemDbEntity mapToDbEntity(ListaItemDomainEntity entity) {
        return new ListaItemDbEntity(
                entity.getId() != null ? entity.getId() : UUID.randomUUID(),
                entity.getDescription(),
                entity.getListaId(),
                entity.getFlagged()
        );
    }
}