package com.gunes.dao;

import com.gunes.dao.base.GenericDao;
import com.gunes.model.entity.Cell;

import java.util.List;

public interface CellDao extends GenericDao<Cell, Long> {

    Long countLetterByBoardId(final Long boardId);

    List getByBoardId(Long boardId);
}
