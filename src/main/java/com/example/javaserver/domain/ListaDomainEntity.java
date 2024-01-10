package com.example.javaserver.domain;

import com.example.javaserver.responses.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListaDomainEntity extends DomainEntity implements Payload {

    @Null(groups = NullId.class)
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("items")
    private List<ListaItemDomainEntity> items;
    @NotNull(groups = NotNullFriendlyId.class)
    @JsonProperty("friendlyId")
    private String friendlyId;

    public ListaDomainEntity(UUID id, List<ListaItemDomainEntity> items, String friendlyId) {
        this.id = id;
        items.forEach(item -> item.setListaId(id));
        this.items = items;
        this.friendlyId = friendlyId;
    }

    public interface NotNullFriendlyId {
    }

    public interface NullId {
    }
}
