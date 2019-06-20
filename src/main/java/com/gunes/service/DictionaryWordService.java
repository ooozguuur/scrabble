package com.gunes.service;

import com.gunes.model.document.DictionaryWord;

import java.util.List;

public interface DictionaryWordService {

    Long count();

    boolean isAcceptableWord(String letters);

    DictionaryWord save(DictionaryWord dictionaryWord);

    void saveAll(List<DictionaryWord> dictionaryWords);
}
