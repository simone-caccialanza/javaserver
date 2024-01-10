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
    @Column(name = "friendly_id", unique = true)
    private String friendlyId;

    public ListaDbEntity(UUID id, List<ListaItemDbEntity> items, String friendlyId) {
        this.id = id;
        this.items = items;
        this.friendlyId = friendlyId;
    }
}
