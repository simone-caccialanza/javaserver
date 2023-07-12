package com.example.javaserver.database.entities;

import com.example.javaserver.domain.DomainEntity;

public interface DomainEntityMapping<T extends DomainEntity> {
    T toDomainEntity();
}
