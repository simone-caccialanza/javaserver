package com.example.javaserver.services;

import com.example.javaserver.database.repositories.ListaItemRepository;
import com.example.javaserver.database.repositories.ListaRepository;
import com.example.javaserver.domain.ListaDomainEntity;
import com.example.javaserver.mappers.ListaItemMapper;
import com.example.javaserver.mappers.ListaMapper;
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

    public ListaDomainEntity save(ListaDomainEntity entity) {
        val listaDbEntity = listaMapper.mapToDbEntity(entity);
        val dbResult = listaRepository.save(listaDbEntity);
        return listaMapper.mapToDomainEntity(dbResult);
    }

    public ListaDomainEntity saveItems(@NotNull ListaDomainEntity entity) {
        val oldListItems = this.get(entity.getId()).get().getItems();
        val newListItem = entity.getItems().stream().map(listaItemMapper::mapToDbEntity).toList();
        val newItemsToSave = newListItem.stream().filter(newItem -> !oldListItems.contains(newItem)).toList();
        val dbResult = listaItemRepository.saveAll(newItemsToSave)
                .stream().map(listaItemMapper::mapToDomainEntity).toList();
        return new ListaDomainEntity(entity.getId(), dbResult);
    }

    public Optional<ListaDomainEntity> get(UUID uuid) {
        val dbResult = listaRepository.findById(uuid);
        return dbResult.map(listaMapper::mapToDomainEntity);
    }

    public void deleteItems(@NotNull ListaDomainEntity entity) {
        val listaItemDbEntityList = entity.getItems().stream().map(listaItemMapper::mapToDbEntity).toList();
        listaItemRepository.deleteAll(listaItemDbEntityList);
    }
}
