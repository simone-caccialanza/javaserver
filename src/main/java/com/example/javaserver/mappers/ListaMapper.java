package com.example.javaserver.mappers;

import com.example.javaserver.database.entities.ListaDbEntity;
import com.example.javaserver.database.entities.ListaItemDbEntity;
import com.example.javaserver.domain.ListaDomainEntity;
import com.example.javaserver.domain.ListaItemDomainEntity;
import org.springframework.stereotype.Component;

@Component
public class ListaMapper implements DomainDbMapper<ListaDomainEntity, ListaDbEntity> {
    @Override
    public ListaDomainEntity mapToDomainEntity(ListaDbEntity entity) {
        return new ListaDomainEntity(entity.getListaId(),
                entity.getItems().stream().map(item ->
                        new ListaItemDomainEntity(item.getItemId(), item.getDescription(), item.getListaId())).toList());
    }

    @Override
    public ListaDbEntity mapToDbEntity(ListaDomainEntity entity) {
        return new ListaDbEntity(entity.getId(), entity.getItems().stream().map(item ->
                new ListaItemDbEntity(item.getId(), item.getDescription(), item.getListaId())).toList());
    }
}