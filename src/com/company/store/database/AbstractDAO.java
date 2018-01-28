package com.company.store.database;

import java.util.List;

public abstract class AbstractDAO<K extends Number, T> {
    public abstract List<T> findAll();

    public abstract T findEntityById(K id);

    public abstract K findIdByEntity(T entity);

    public abstract boolean delete(K id);

    public abstract K create(T entity);

    public abstract boolean update(T entity);
}
