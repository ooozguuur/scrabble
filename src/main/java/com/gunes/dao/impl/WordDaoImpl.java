package com.gunes.dao.impl;

import com.gunes.dao.WordDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Word;
import org.springframework.stereotype.Repository;


@Repository
public class WordDaoImpl extends GenericJpaDao<Word, Long> implements WordDao {

    @Override
    public Word createEntityObject() {
        return new Word();
    }

    @Override
    public Class getPersistentClass() {
        return Word.class;
    }
}