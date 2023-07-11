package com.example.javaserver.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
public abstract class DbEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    protected UUID id;
    
}
