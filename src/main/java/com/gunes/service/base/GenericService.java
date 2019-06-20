package com.gunes.service.base;

public interface GenericService<T> {

    T createEntityObject();

    T persist(T entity);

    T update(T entity);

    T findById(long id);


}
