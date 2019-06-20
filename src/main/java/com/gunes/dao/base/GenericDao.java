package com.gunes.dao.base;

public interface GenericDao<T> {

    Class<T> getPersistentClass();

    T createEntityObject();

    T persist(T entity);

    T update(T entity);

    T findById(long id);

}
