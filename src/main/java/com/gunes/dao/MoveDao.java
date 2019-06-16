package com.gunes.dao;

import com.gunes.dao.base.GenericDao;
import com.gunes.model.entity.Move;

public interface MoveDao extends GenericDao<Move, Long> {

    Integer getLastSequenceByBoardId(Long boardId);

    Move getBoardContent(Long boardId, Integer sequnce);
}
