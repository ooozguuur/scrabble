package com.gunes.dao;

import com.gunes.dao.base.GenericDao;
import com.gunes.model.entity.Word;

import java.util.List;

public interface WordDao extends GenericDao<Word> {

    List<Word> getWords(Long boardId);

}
