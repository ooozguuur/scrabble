package com.gunes.dao.impl;

import com.gunes.dao.CellDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Cell;
import org.springframework.stereotype.Repository;


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
    public boolean hasCellByBoard(final long boardId) {
        String sql = "SELECT COUNT(ce) > 0 from Cell ce where ce.board.id = :boardId";
        return (boolean) getEntityManager().createQuery(sql).getSingleResult();
    }
}