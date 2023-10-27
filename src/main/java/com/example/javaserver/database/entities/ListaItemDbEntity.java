package com.example.javaserver.database.entities;

import com.example.javaserver.domain.ListaItemDomainEntity;
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
public class ListaItemDbEntity extends DbEntity implements DomainEntityMapping<ListaItemDomainEntity> {

    @Column(name = "itemId")
    private String itemId;
    @Column(name = "description")
    private String description;
    @Column(name = "lista_id")
    private UUID listaId;

    @Override
    public ListaItemDomainEntity toDomainEntity() {
        return new ListaItemDomainEntity(itemId, description, listaId);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListaItemDbEntity that = (ListaItemDbEntity) o;

        return itemId.equals(that.itemId);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }
}
