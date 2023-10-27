package com.example.javaserver.mappers;

import com.example.javaserver.database.entities.DbEntity;
import com.example.javaserver.domain.DomainEntity;

public interface DomainDbMapper<T extends DomainEntity, U extends DbEntity> {
    T mapToDomainEntity(U entity);

    U mapToDbEntity(T entity);
}
