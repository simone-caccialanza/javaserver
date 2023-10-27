package com.example.javaserver.database.repositories;

import com.example.javaserver.database.entities.ListaItemDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListaItemRepository extends JpaRepository<ListaItemDbEntity, UUID> {
}
