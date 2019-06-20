package com.gunes.service.base.impl;

import com.gunes.dao.base.GenericDao;
import com.gunes.service.base.GenericService;

public abstract class GenericServiceImpl<T> implements GenericService<T> {

    private GenericDao<T> genericDao;

    public GenericServiceImpl(GenericDao<T> genericDao) {
        this.genericDao = genericDao;
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
        return genericDao.findById(id);
    }
}

