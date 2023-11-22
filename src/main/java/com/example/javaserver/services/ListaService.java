package com.example.javaserver.services;

import com.example.javaserver.database.repositories.ListaItemRepository;
import com.example.javaserver.database.repositories.ListaRepository;
import com.example.javaserver.domain.ListaDomainEntity;
import com.example.javaserver.mappers.ListaItemMapper;
import com.example.javaserver.mappers.ListaMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ListaService {

    private final ListaRepository listaRepository;

    private final ListaItemRepository listaItemRepository;

    private final ListaMapper listaMapper;

    private final ListaItemMapper listaItemMapper;

    @Autowired
    public ListaService(ListaRepository listaRepository, ListaItemRepository listaItemRepository, ListaMapper listaMapper, ListaItemMapper listaItemMapper) {
        this.listaRepository = listaRepository;
        this.listaItemRepository = listaItemRepository;
        this.listaMapper = listaMapper;
        this.listaItemMapper = listaItemMapper;
    }

    @Transactional
    public ListaDomainEntity save(ListaDomainEntity entity) {
        val listaDbEntity = listaMapper.mapToDbEntity(entity);
        transactionalSaveListaId(listaDbEntity.getId());
        val dbResult = listaRepository.save(listaDbEntity);
        return listaMapper.mapToDomainEntity(dbResult);
    }

    public ListaDomainEntity saveItems(@NotNull ListaDomainEntity entity) {
        val newItemsToSave = entity.getItems().stream()
//                .filter(item -> item.getId() == null)
                .map(listaItemMapper::mapToDbEntity)
                .toList();
        val dbResult = listaItemRepository.saveAll(newItemsToSave)
                .stream().map(listaItemMapper::mapToDomainEntity).toList();
        entity.setItems(dbResult);
        return entity;
    }

    public Optional<ListaDomainEntity> get(UUID uuid) {
        val dbResult = listaRepository.findById(uuid);
        return dbResult.map(listaMapper::mapToDomainEntity);
    }

    public void deleteItems(@NotNull ListaDomainEntity entity) {
        val listaItemDbEntityList = entity.getItems().stream().map(listaItemMapper::mapToDbEntity).toList();
        listaItemRepository.deleteAll(listaItemDbEntityList);
    }

    @Transactional
    private void transactionalSaveListaId(UUID uuid) {
        listaRepository.saveListId(uuid);
    }
}
