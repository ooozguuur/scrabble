package com.gunes.dao;

import com.gunes.dao.base.GenericDao;
import com.gunes.model.entity.Move;

import java.util.List;

public interface MoveDao extends GenericDao<Move> {

    Integer getLastSequenceByBoardId(Long boardId);

    List<Move> getBoardContent(Long boardId, Integer sequence);
}
