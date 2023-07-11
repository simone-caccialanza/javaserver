package com.example.javaserver.services;

import java.util.Optional;

public interface RepositoryService<T, ID>{
    T save(T entity);
    T update(T entity);
    Optional<T> get(ID id);
}
