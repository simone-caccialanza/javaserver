package com.example.javaserver.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(name = "lista_item")
@Getter
public class ListaItemDbEntity extends DbEntity {

    @Column(name = "description")
    private String description;
    @Column(name = "lista_id")
    private UUID listaId;
    @Column(name = "checked")
    private Boolean checked = false;

    public ListaItemDbEntity(UUID id, String description, UUID listaId, Boolean checked) {
        this.id = id;
        this.description = description;
        this.listaId = listaId;
        this.checked = checked != null && checked; //if checked is null, default is false
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListaItemDbEntity that = (ListaItemDbEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
