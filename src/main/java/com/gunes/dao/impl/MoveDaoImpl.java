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
        return (Integer) getEntityManager().createQuery("select max(m.sequence) from Move m where m.board.id = :boardId ")
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    @Override
    public Move getBoardContent(final Long boardId, final Integer sequnce) {
        return (Move) getEntityManager().createQuery("select mo from Move mo where mo.board.id = :boardId and mo.sequence =:sequnce")
                .setParameter("boardId", boardId)
                .setParameter("sequnce", sequnce)
                .getSingleResult();
    }
}