package com.gunes.service.impl;

import com.gunes.dao.DictionaryWordDao;
import com.gunes.model.document.DictionaryWord;
import com.gunes.service.DictionaryWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryWordServiceImpl implements DictionaryWordService {

    private DictionaryWordDao dictionaryWordDao;

    @Autowired
    public DictionaryWordServiceImpl(final DictionaryWordDao dictionaryWordDao) {
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

    @Override
    public DictionaryWord save(final DictionaryWord dictionaryWord) {
        return dictionaryWordDao.save(dictionaryWord);
    }

    @Override
    public void saveAll(final List<DictionaryWord> dictionaryWords) {
        dictionaryWordDao.saveAll(dictionaryWords);
    }
}
