package com.gunes.dao.impl;

import com.gunes.dao.DictionaryWordDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.DictionaryWord;
import org.springframework.stereotype.Repository;


@Repository
public class DictionaryWordDaoImpl extends GenericJpaDao<DictionaryWord, Long> implements DictionaryWordDao {

    @Override
    public DictionaryWord createEntityObject() {
        return new DictionaryWord();
    }

    @Override
    public Class getPersistentClass() {
        return DictionaryWord.class;
    }

    @Override
    public Long count() {
        String sql = "SELECT count(dw) from DictionaryWord dw";
        return getEntityManager().createQuery(sql, Long.class).getSingleResult();
    }
}