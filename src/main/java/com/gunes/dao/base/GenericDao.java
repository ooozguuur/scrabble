package com.gunes.dao.base;

import java.io.Serializable;

public interface GenericDao<T, ID extends Serializable> {

    Class<T> getPersistentClass();

    T createEntityObject();

    T save(T entity);

    T update(T entity);

    T findById(long id);


}
