package com.example.javaserver.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaItemDomainEntity extends DomainEntity {

    private UUID id;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID listaId;
    private Boolean flagged;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListaItemDomainEntity that = (ListaItemDomainEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
