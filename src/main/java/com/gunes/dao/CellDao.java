package com.gunes.dao;

import com.gunes.dao.base.GenericDao;
import com.gunes.model.entity.Cell;

public interface CellDao extends GenericDao<Cell, Long> {

    boolean hasCellByBoard(long boardId);

}
