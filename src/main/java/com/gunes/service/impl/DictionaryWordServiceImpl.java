package com.gunes.service.impl;

import com.gunes.dao.DictionaryWordDao;
import com.gunes.model.entity.DictionaryWord;
import com.gunes.service.DictionaryWordService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryWordServiceImpl extends GenericServiceImpl<DictionaryWord, Long> implements DictionaryWordService {

    private DictionaryWordDao dictionaryWordDao;

    @Autowired
    public DictionaryWordServiceImpl(final DictionaryWordDao dictionaryWordDao) {
        super(dictionaryWordDao);
        this.dictionaryWordDao = dictionaryWordDao;
    }

    @Override
    public Long count() {
        return dictionaryWordDao.count();
    }

    @Override
    public boolean isAcceptableWord(final String letters) {
        return dictionaryWordDao.isAcceptableWord(letters.toLowerCase());
    }


}
