package com.example.javaserver.database.repositories;

import com.example.javaserver.database.entities.ListaDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListaRepository extends JpaRepository<ListaDbEntity, UUID> {

    @Modifying
    @Query("INSERT INTO lista (id) VALUES (:uuid)")
    void saveListId(@Param("uuid") UUID uuid);
}
