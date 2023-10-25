package com.example.javaserver.services;

import com.example.javaserver.database.entities.ListaDbEntity;
import com.example.javaserver.database.repositories.ListaRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ListaService implements RepositoryService<ListaDbEntity, UUID> {

    private final ListaRepository listaRepository;

    @Autowired
    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    @Override
    public ListaDbEntity save(ListaDbEntity entity) {
        return listaRepository.save(entity);
    }

    @Override
    public ListaDbEntity update(@NotNull ListaDbEntity entity) {
        return listaRepository.save(entity);
    }

    @Override
    public Optional<ListaDbEntity> get(UUID uuid) {
        return listaRepository.findById(uuid);
    }
}
