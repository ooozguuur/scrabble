package com.gunes.service;

import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.base.GenericService;

import java.util.List;

public interface CellService extends GenericService<Cell, Long> {

    boolean hasCellByBoard(long boardId);

    List<Cell> splitTheWord(Word word);
}
