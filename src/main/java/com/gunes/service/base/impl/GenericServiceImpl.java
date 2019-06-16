package com.gunes.service.base.impl;

import com.gunes.dao.base.GenericDao;
import com.gunes.service.base.GenericService;

import java.io.Serializable;

public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {

    private GenericDao<T, ID> genericDao;

    public GenericServiceImpl(GenericDao<T, ID> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Class<T> getPersistentClass() {
        return this.genericDao.getPersistentClass();
    }

    @Override
    public T createEntityObject() {
        return this.genericDao.createEntityObject();
    }

    @Override
    public T persist(final T entity) {
        return genericDao.persist(entity);
    }

    @Override
    public T update(final T entity) {
        return genericDao.update(entity);
    }

    @Override
    public T findById(final long id) {
        return findById(id);
    }
}

