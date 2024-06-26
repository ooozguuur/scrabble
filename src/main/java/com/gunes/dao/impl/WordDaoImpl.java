package com.gunes.dao.impl;

import com.gunes.dao.WordDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Word;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class WordDaoImpl extends GenericJpaDao<Word> implements WordDao {

    @Override
    public Word createEntityObject() {
        return new Word();
    }

    @Override
    public Class getPersistentClass() {
        return Word.class;
    }

    @Override
    public List<Word> getWords(final Long boardId) {
        String sql = " SELECT distinct (wo) FROM Word wo join wo.cells ce join ce.board bo WHERE bo.id = :boardId ";
        return getEntityManager().createQuery(sql, Word.class).setParameter("boardId", boardId).getResultList();
    }
}