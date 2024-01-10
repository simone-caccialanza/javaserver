package com.example.javaserver.database.repositories;

import com.example.javaserver.database.entities.ListaDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListaRepository extends JpaRepository<ListaDbEntity, UUID> {

    @Modifying
    @Query(
            value = "INSERT INTO lista (id, friendly_id) VALUES (:uuid, :friendly_id)",
            nativeQuery = true
    )
    void saveListId(@Param("uuid") UUID uuid, @Param("friendly_id") String friendlyId);

    @Query(
            value = "SELECT * FROM lista WHERE friendly_id = :friendly_id",
            nativeQuery = true
    )
    Optional<ListaDbEntity> findByFriendlyId(@Param("friendly_id") String friendlyId);

}
