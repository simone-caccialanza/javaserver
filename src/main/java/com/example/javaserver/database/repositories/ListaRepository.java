package com.example.javaserver.database.repositories;

import com.example.javaserver.database.entities.ListaDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListaRepository extends JpaRepository<ListaDbEntity, UUID> {
}
