package com.gunes.dao.impl;

import com.gunes.dao.CellDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Cell;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CellDaoImpl extends GenericJpaDao<Cell> implements CellDao {

    @Override
    public Cell createEntityObject() {
        return new Cell();
    }

    @Override
    public Class getPersistentClass() {
        return Cell.class;
    }

    @Override
    public List<Cell> getByBoardId(final Long boardId) {
        String sql = "SELECT ce from Cell ce where ce.board.id = :boardId";
        return getEntityManager().createQuery(sql, Cell.class).setParameter("boardId", boardId).getResultList();
    }
}