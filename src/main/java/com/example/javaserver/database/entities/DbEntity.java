package com.example.javaserver.database.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;

@MappedSuperclass
public abstract class DbEntity {

    @Id
    @Getter
    protected UUID id;

}
