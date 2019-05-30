package com.gunes.dao.impl;

import com.gunes.dao.MoveDao;
import com.gunes.dao.base.impl.GenericJpaDao;
import com.gunes.model.entity.Move;
import org.springframework.stereotype.Repository;


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
    public int getLastMoveSequenceByBoardId(final long boardId) {
        return (int) getEntityManager().createQuery("select max(m.sequence) from Move m where m.board.id = :boardId ")
                .setParameter("boardId", boardId)
                .getSingleResult();
    }
}