package com.example.javaserver.domain;

import com.example.javaserver.database.entities.ListaItemDbEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ListaItemDomainEntity extends DomainEntity implements DbEntityMapping<ListaItemDbEntity> {

    private String id;
    private String description;

    @Override
    public ListaItemDbEntity toDbEntity() {
        return new ListaItemDbEntity(id, description, null);
    }
}
