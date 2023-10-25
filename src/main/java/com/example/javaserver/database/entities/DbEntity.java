package com.example.javaserver.database.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;

@MappedSuperclass
public abstract class DbEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    protected UUID id;
    
}
