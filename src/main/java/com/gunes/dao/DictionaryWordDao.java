package com.gunes.dao;

import com.gunes.model.document.DictionaryWord;

import java.util.List;

public interface DictionaryWordDao {

    Long count();

    boolean isAcceptableWord(String letters);

    List<DictionaryWord> saveAll(List<DictionaryWord> entity);

    DictionaryWord save(DictionaryWord entity);
}
