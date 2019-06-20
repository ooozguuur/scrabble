package com.gunes.dao.impl;

import com.gunes.dao.DictionaryWordDao;
import com.gunes.enums.ApplicationConstants;
import com.gunes.model.document.DictionaryWord;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DictionaryWordDaoImpl implements DictionaryWordDao {

    @Autowired
    protected Datastore datastore;

    @Override
    public Long count() {
        return datastore.getCollection(DictionaryWord.class).count();
    }

    @Override
    public boolean isAcceptableWord(final String letters) {
        return datastore
                .find(DictionaryWord.class)
                .field("translations").exists()
                .filter("translations." + ApplicationConstants.DEFAULT_LANG, letters).count() > 0;

    }

    @Override
    public List<DictionaryWord> saveAll(List<DictionaryWord> entity) {
        datastore.save(entity);
        return entity;
    }

    @Override
    public DictionaryWord save(final DictionaryWord entity) {
        datastore.save(entity);
        return entity;
    }
}