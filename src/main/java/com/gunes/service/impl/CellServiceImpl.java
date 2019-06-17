package com.gunes.service.impl;

import com.gunes.dao.CellDao;
import com.gunes.enums.DirectionType;
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
import java.util.stream.IntStream;

@Service
public class CellServiceImpl extends GenericServiceImpl<Cell, Long> implements CellService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellServiceImpl.class);

    private CellDao cellDao;

    @Autowired
    public CellServiceImpl(final CellDao cellDao) {
        super(cellDao);
        this.cellDao = cellDao;
    }

    @Override
    public Long countLetterByBoardId(final Long boardId) {
        return cellDao.countLetterByBoardId(boardId);
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
    public String cellToString(final List<Cell> cells) {
        if (cells == null || cells.isEmpty()) {
            LOGGER.error("Cell is null.");
            throw new NullPointerException("Cell is null.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        cells.forEach(cell -> stringBuilder.append(cell.getCharacter()));
        return stringBuilder.toString();
    }
}
