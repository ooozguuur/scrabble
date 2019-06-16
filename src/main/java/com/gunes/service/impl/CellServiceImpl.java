package com.gunes.service.impl;

import com.gunes.dao.CellDao;
import com.gunes.enums.DirectionType;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.CellService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellServiceImpl extends GenericServiceImpl<Cell, Long> implements CellService {

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
        List<Cell> cells = new ArrayList<>(word.getLetters().length());
        char[] chars = word.getLetters().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            Cell cell = this.createEntityObject();
            cell.setCharacter(c);
            if (word.getDirectionType() == DirectionType.HORIZONTAL) {
                cell.setxPosition(word.getHorizontalStartingPoint());
                cell.setyPosition(word.getVerticalStartingPoint() + i);
            } else {
                cell.setxPosition(word.getHorizontalStartingPoint() + i);
                cell.setyPosition(word.getVerticalStartingPoint());
            }
            cells.add(cell);
        }
        return cells;
    }

    @Override
    public List<Cell> getByBoardId(final Long boardId) {
        return cellDao.getByBoardId(boardId);
    }
}
