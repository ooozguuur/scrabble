package com.gunes.service.impl;

import com.google.common.collect.Lists;
import com.gunes.dao.CellDao;
import com.gunes.enums.ApplicationConstants;
import com.gunes.enums.CharacterScore;
import com.gunes.enums.DirectionType;
import com.gunes.exceptions.CellCharacterNotUpdatedException;
import com.gunes.exceptions.WordNotFoundException;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.CellService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class CellServiceImpl extends GenericServiceImpl<Cell> implements CellService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellServiceImpl.class);

    private CellDao cellDao;

    @Autowired
    public CellServiceImpl(final CellDao cellDao) {
        super(cellDao);
        this.cellDao = cellDao;
    }

    @Override
    public List<Cell> splitTheWord(final Word word) {
        if (word == null || word.getLetters() == null || word.getLetters().isEmpty()) {
            LOGGER.error("Word not found.");
            throw new WordNotFoundException("Word not found ");
        }
        List<Cell> cells = new ArrayList<>(word.getLetters().length());
        char[] chars = word.getLetters().toCharArray();
        IntStream.range(0, chars.length).forEach(i -> {
            final char c = chars[i];
            Cell cell = cellDao.createEntityObject();
            cell.setCharacter(c);
            if (word.getDirectionType() == DirectionType.HORIZONTAL) {
                cell.setxPosition(word.getHorizontalStartingPoint());
                cell.setyPosition(word.getVerticalStartingPoint() + i);
            } else {
                cell.setxPosition(word.getHorizontalStartingPoint() + i);
                cell.setyPosition(word.getVerticalStartingPoint());
            }
            cells.add(cell);
        });
        return cells;
    }

    @Override
    public List<Cell> getByBoardId(final Long boardId) {
        return cellDao.getByBoardId(boardId);
    }

    @Override
    public Cell[][] listToArray(List<Cell> cells) {
        Cell[][] cellArray =  new Cell[ApplicationConstants.DEFAULT_HORIZONTAL_SIZE - 1][ApplicationConstants.DEFAULT_HORIZONTAL_SIZE - 1];
        cells.forEach(cell -> cellArray[cell.getxPosition()][cell.getyPosition()] = cell);
        return cellArray;
    }

    @Override
    public String cellsToString(final List<Cell> cells) {
        if (cells == null || cells.isEmpty()) {
           return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        cells.forEach(cell -> stringBuilder.append(cell.getCharacter()));
        return stringBuilder.toString();
    }

    @Override
    public List<Cell> getUpFillCells(Cell cell, final Cell[][] characters) {
        List<Cell> cells = new ArrayList<>();
        if (characters[cell.getxPosition()][cell.getyPosition() - 1] != null) {
            for (int i = cell.getyPosition() - 1; i >= 0; i--) {
                if (characters[cell.getxPosition()][i] != null) {
                    cells.add(characters[cell.getxPosition()][i]);
                } else {
                    break;
                }
            }
            cells = Lists.reverse(cells);
        }
        return cells;
    }

    @Override
    public List<Cell> getDownFillCells(final Cell cell, final Cell[][] characters) {
        List<Cell> cells = new ArrayList<>();
        if (characters[cell.getxPosition()][cell.getyPosition() + 1] != null) {
            for (int i = cell.getyPosition() + 1; i < ApplicationConstants.DEFAULT_VERTICAL_SIZE -1; i++) {
                if (characters[cell.getxPosition()][i] != null) {
                    cells.add(characters[cell.getxPosition()][i]);
                } else {
                    break;
                }
            }
        }
        return cells;
    }

    @Override
    public List<Cell> getLeftFillCells(final Cell cell, final Cell[][] characters) {
        List<Cell> cells = new ArrayList<>();
        if (characters[cell.getxPosition() - 1][cell.getyPosition()] != null) {
            for (int i = cell.getxPosition() - 1; i >= 0; i--) {
                if (characters[i][cell.getyPosition()] != null) {
                    cells.add(characters[i][cell.getyPosition()]);
                } else {
                    break;
                }
            }
            cells = Lists.reverse(cells);
        }
        return cells;
    }

    @Override
    public List<Cell> getRightFillCells(final Cell cell, final Cell[][] characters) {
        List<Cell> cells = new ArrayList<>();
        if (characters[cell.getxPosition() + 1][cell.getyPosition()] != null) {
            for (int i = cell.getxPosition() + 1; i < ApplicationConstants.DEFAULT_HORIZONTAL_SIZE; i++) {
                if (characters[i][cell.getyPosition()] != null) {
                    cells.add(characters[i][cell.getyPosition()]);
                } else {
                    break;
                }
            }
        }
        return cells;
    }

    @Override
    public List<Cell> getCommonCells(final List<Cell> newCharacters, final Cell[][] characters) {
        List<Cell> cells = new ArrayList<>();
        newCharacters.forEach(cell -> {
            if (characters[cell.getxPosition()][cell.getyPosition()] != null) {
                cells.add(characters[cell.getxPosition()][cell.getyPosition()]);
            }
        });
        return cells;
    }

    @Override
    public void checkCellsInCharacter(final List<Cell> newCharacters, final Cell[][] cells) {
        for (final Cell letter : newCharacters) {
            if (cells[letter.getxPosition()][letter.getyPosition()] != null && cells[letter.getxPosition()][letter.getyPosition()].getCharacter() != letter.getCharacter()) {
                LOGGER.error("Can not change cell in character.");
                throw new CellCharacterNotUpdatedException("Can not change cell in character");
            }
        }
    }

    @Override
    public int getScoreByCells(final Set<Cell> cells) {
        int score = 0;
        for (Cell cell : cells) {
            score += CharacterScore.getScore(cell.getCharacter());
        }
        return score;
    }
}
