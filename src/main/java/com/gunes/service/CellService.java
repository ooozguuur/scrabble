package com.gunes.service;

import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.base.GenericService;

import java.util.List;
import java.util.Set;

public interface CellService extends GenericService<Cell> {

    List<Cell> splitTheWord(Word word);

    List<Cell> getByBoardId(Long id);

    String cellsToString(List<Cell> cells);

    Cell[][] listToArray(List<Cell> addedCharacters);

    List<Cell> getUpFillCells(Cell cell, Cell[][] characters);

    List<Cell> getDownFillCells(Cell cell, Cell[][] characters);

    List<Cell> getLeftFillCells(Cell cell, Cell[][] characters);

    List<Cell> getRightFillCells(Cell cell, Cell[][] characters);

    void checkCellsInCharacter(List<Cell> newCharacters, Cell[][] characters);

    List<Cell> getCommonCells(List<Cell> newCharacters, Cell[][] characters);

    int getScoreByCells(Set<Cell> cells);
}
