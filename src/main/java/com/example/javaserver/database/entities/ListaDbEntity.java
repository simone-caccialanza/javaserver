package com.example.javaserver.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity(name = "lista")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lista")
@Getter
public class ListaDbEntity extends DbEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lista_id")
    @Column(name = "items")
    private List<ListaItemDbEntity> items;

    public ListaDbEntity(UUID id, List<ListaItemDbEntity> items) {
        this.id = id;
        this.items = items;
    }
}
