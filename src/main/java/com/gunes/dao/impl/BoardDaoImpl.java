package com.gunes.dao.impl;

import com.gunes.dao.BoardDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Board;
import org.springframework.stereotype.Repository;


@Repository
public class BoardDaoImpl extends GenericJpaDao<Board> implements BoardDao {

    @Override
    public Board createEntityObject() {
        return new Board();
    }

    @Override
    public Class getPersistentClass() {
        return Board.class;
    }
}