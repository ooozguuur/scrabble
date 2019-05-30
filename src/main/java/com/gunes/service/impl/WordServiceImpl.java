package com.gunes.service.impl;

import com.gunes.dao.WordDao;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.CellService;
import com.gunes.service.WordService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordServiceImpl extends GenericServiceImpl<Word, Long> implements WordService {

    @Autowired
    private CellService cellService;

    private WordDao wordDao;

    @Autowired
    public WordServiceImpl(final WordDao wordDao) {
        super(wordDao);
        this.wordDao = wordDao;
    }

    @Override
    public List<Word> createWordsByBoard(final Board board, Word word) {
        Set<Word> words = new HashSet<>();
        boolean hasCell = cellService.hasCellByBoard(board.getId());
        if (!hasCell) {
            word = this.firstWordByMove(board, word);
            words.add(word);
        }
        return null;
    }

    private Word firstWordByMove(final Board board, Word word) {
        if (isAvailableCoordinates(board.getHorizontalSize(), board.getVerticalSize(), word)) {
            List<Cell> cells = cellService.splitTheWord(word);
            cells.forEach(cell -> cell.setBoard(board));
            word.getCells().addAll(cells);
        }
        return null;
    }
}
