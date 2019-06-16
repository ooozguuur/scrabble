package com.gunes.service.base;

import java.io.Serializable;

public interface GenericService<T, ID extends Serializable> {

    Class<T> getPersistentClass();

    T createEntityObject();

    T persist(T entity);

    T update(T entity);

    T findById(long id);


}
