package com.example.javaserver.database.repositories;

import com.example.javaserver.database.entities.PersonDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonDbEntity, UUID> {
}
