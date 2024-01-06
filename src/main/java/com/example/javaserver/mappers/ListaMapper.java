package com.example.javaserver.mappers;

import com.example.javaserver.database.entities.ListaDbEntity;
import com.example.javaserver.database.entities.ListaItemDbEntity;
import com.example.javaserver.domain.ListaDomainEntity;
import com.example.javaserver.domain.ListaItemDomainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ListaMapper implements DomainDbMapper<ListaDomainEntity, ListaDbEntity> {

    private final DomainDbMapper<ListaItemDomainEntity, ListaItemDbEntity> itemsMapper;

    @Autowired
    public ListaMapper(DomainDbMapper<ListaItemDomainEntity, ListaItemDbEntity> itemsMapper) {
        this.itemsMapper = itemsMapper;
    }

    @Override
    public ListaDomainEntity mapToDomainEntity(ListaDbEntity entity) {
        return new ListaDomainEntity(entity.getId(),
                entity.getItems().stream().map(item ->
                        new ListaItemDomainEntity(item.getId(), item.getDescription(), item.getListaId(), item.getFlagged())).toList());
    }

    @Override
    public ListaDbEntity mapToDbEntity(ListaDomainEntity entity) {
        return new ListaDbEntity(
                entity.getId() != null ? entity.getId() : UUID.randomUUID(),
                entity.getItems().stream().map(itemsMapper::mapToDbEntity)
                        .toList());
    }
}