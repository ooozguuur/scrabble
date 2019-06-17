package com.gunes.dao.impl;

import com.gunes.dao.MoveDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Move;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MoveDaoImpl extends GenericJpaDao<Move, Long> implements MoveDao {

    @Override
    public Move createEntityObject() {
        return new Move();
    }

    @Override
    public Class getPersistentClass() {
        return Move.class;
    }

    @Override
    public Integer getLastSequenceByBoardId(final Long boardId) {
        String sql = " select max(m.sequence) from Move m where m.board.id = :boardId ";
        return getEntityManager().createQuery(sql, Integer.class).setParameter("boardId", boardId).getSingleResult();
    }

    @Override
    public List<Move> getBoardContent(final Long boardId, final Integer sequence) {
        String sql = " select mo from Move mo join fetch mo.words wo join fetch wo.cells where mo.board.id = :boardId and mo.sequence <= :sequence ";
        return getEntityManager().createQuery(sql, Move.class)
                .setParameter("boardId", boardId)
                .setParameter("sequence", sequence)
                .getResultList();
    }
}