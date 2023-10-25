package com.example.javaserver.database.entities;

import com.example.javaserver.domain.ListaDomainEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lista")
public class ListaDbEntity extends DbEntity implements DomainEntityMapping<ListaDomainEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID listaId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lista_id")
    @Column(name = "items")
    private List<ListaItemDbEntity> items;

    @Override
    public ListaDomainEntity toDomainEntity() {
        return new ListaDomainEntity(listaId, items.stream().map(ListaItemDbEntity::toDomainEntity).toList());
    }
}
