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
}

