package com.gunes.dao.impl;

import com.gunes.dao.CellDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Cell;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CellDaoImpl extends GenericJpaDao<Cell, Long> implements CellDao {

    @Override
    public Cell createEntityObject() {
        return new Cell();
    }

    @Override
    public Class getPersistentClass() {
        return Cell.class;
    }

    @Override
    public Long countLetterByBoardId(final Long boardId) {
        String sql = "SELECT COUNT(ce) from Cell ce where ce.board.id = :boardId";
        return (Long) getEntityManager().createQuery(sql).setParameter("boardId", boardId).getSingleResult();
    }

    @Override
    public List<Cell> getByBoardId(final Long boardId) {
        String sql = "SELECT ce from Cell ce where ce.board.id = :boardId";
        return (List<Cell>) getEntityManager().createQuery(sql).setParameter("boardId", boardId).getResultList();
    }
}