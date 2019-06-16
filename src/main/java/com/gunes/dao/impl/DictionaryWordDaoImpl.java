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

    @Override
    public boolean isAcceptableWord(final String letters) {
        String sql = "select count(dw) > 0 from DictionaryWord dw where dw.word = :letters ";
        return getEntityManager().createQuery(sql, Boolean.class).getSingleResult();
    }
}