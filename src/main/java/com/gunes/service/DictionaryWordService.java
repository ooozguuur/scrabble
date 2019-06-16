package com.gunes.service;

import com.gunes.model.entity.DictionaryWord;
import com.gunes.service.base.GenericService;

public interface DictionaryWordService extends GenericService<DictionaryWord, Long> {

    Long count();
}
