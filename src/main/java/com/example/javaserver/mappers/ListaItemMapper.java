package com.example.javaserver.mappers;

import com.example.javaserver.database.entities.ListaItemDbEntity;
import com.example.javaserver.domain.ListaItemDomainEntity;
import org.springframework.stereotype.Component;

@Component
public class ListaItemMapper implements DomainDbMapper<ListaItemDomainEntity, ListaItemDbEntity> {
    @Override
    public ListaItemDomainEntity mapToDomainEntity(ListaItemDbEntity entity) {
        return new ListaItemDomainEntity(entity.getItemId(), entity.getDescription(), entity.getListaId());
    }

    @Override
    public ListaItemDbEntity mapToDbEntity(ListaItemDomainEntity entity) {
        return new ListaItemDbEntity(entity.getId(), entity.getDescription(), entity.getListaId());
    }
}