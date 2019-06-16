package com.gunes.dao;

import com.gunes.dao.base.GenericDao;
import com.gunes.model.entity.DictionaryWord;

public interface DictionaryWordDao extends GenericDao<DictionaryWord, Long> {

    Long count();

    boolean isAcceptableWord(String letters);
}
