package com.gunes.service;

import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.base.GenericService;

import java.util.List;

public interface CellService extends GenericService<Cell, Long> {

    Long countLetterByBoardId(final Long boardId);

    List<Cell> splitTheWord(Word word);

    List getByBoardId(Long id);

    String cellToString(List<Cell> cells);
}
