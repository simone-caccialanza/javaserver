package com.example.javaserver.domain;

import com.example.javaserver.database.entities.DbEntity;

public interface DbEntityMapping<T extends DbEntity> {
    T toDbEntity();
}
