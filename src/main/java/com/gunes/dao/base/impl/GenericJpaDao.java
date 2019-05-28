package com.gunes.dao.base.impl;

import com.gunes.dao.base.GenericDao;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;


@Transactional
public abstract class GenericJpaDao<T, ID extends Serializable> implements GenericDao<T, ID> {

    private static final Logger LOGGER = Logger.getLogger(GenericJpaDao.class);

    private Class<T> persistentClass;

    @PersistenceContext
    private EntityManager entityManager;

    public GenericJpaDao() {
    }

    public GenericJpaDao(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Class<T> getPersistentClass() {
        this.persistentClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return persistentClass;
    }

    @Override
    public T createEntityObject() {
        try {
            return this.persistentClass.newInstance();
        } catch (final InstantiationException e) {
            LOGGER.debug("Can not call newInstance() on the Class for {}" + persistentClass.getName(), e);
        } catch (final IllegalAccessException e) {
            LOGGER.debug("Can not created Class for {}" + persistentClass.getName(), e);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(final long id) {
        T entity = getEntityManager().find(getPersistentClass(), id);
        return entity;
    }

    @Override
    @Transactional
    public T save(final T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(final T entity) {
        T mergedEntity = getEntityManager().merge(entity);
        return mergedEntity;
    }

}
