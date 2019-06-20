package com.gunes.service.impl;

import com.gunes.dao.WordDao;
import com.gunes.enums.DirectionType;
import com.gunes.exceptions.NotAcceptableWordException;
import com.gunes.exceptions.WordCoordinatesNotAcceptableException;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.CellService;
import com.gunes.service.DictionaryWordService;
import com.gunes.service.WordService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordServiceImpl extends GenericServiceImpl<Word> implements WordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordServiceImpl.class);

    private final CellService cellService;

    private final DictionaryWordService dictionaryWordService;

    private WordDao wordDao;

    @Autowired
    public WordServiceImpl(final WordDao wordDao, final CellService cellService,
                           final DictionaryWordService dictionaryWordService) {
        super(wordDao);
        this.wordDao = wordDao;
        this.cellService = cellService;
        this.dictionaryWordService = dictionaryWordService;
    }

    @Override
    public List<Word> getWords(final Long boardId) {
        return wordDao.getWords(boardId);
    }

    @Override
    public Set<Word> createWordsByBoard(final Board board, Word word) {
        if (!isAvailableCoordinates(board.getHorizontalSize(), board.getVerticalSize(), word)) {
            LOGGER.error("Word coordinates not acceptable : Horizontal {} Vertical {}", word.getHorizontalStartingPoint(), word.getVerticalStartingPoint());
            throw new WordCoordinatesNotAcceptableException(" Word coordinates not acceptable.");
        }
        Set<Word> words = getAcceptableWords(board, word);
        if (words.isEmpty()) {
            LOGGER.error("The word {} ,didn't add", word.getLetters());
            throw new NotAcceptableWordException("Acceptable Word Not found");
        }
        words.forEach(this::persist);
        return words;
    }

    private Set<Word> getAcceptableWords(Board board, Word newWord) {
        List<Cell> addedCharacters = cellService.getByBoardId(board.getId());
        List<Cell> newCharacters = cellService.splitTheWord(newWord);
        Set<Word> newWords = new HashSet<>();
        if (!addedCharacters.isEmpty()) {
            Cell[][] characters = cellService.listToArray(addedCharacters);
            cellService.checkCellsInCharacter(newCharacters, characters);
            Word word = this.initializeNewWord(newCharacters, characters, newWord.getDirectionType());
            newWords.add(word);
            newWords.addAll(this.getAdjacentWords(newCharacters, characters, newWord.getDirectionType()));
        } else {
            newCharacters.forEach(cell -> cell.setBoard(board));
            Set<Cell> newCharactersSet = new HashSet<>(newCharacters);
            newWord.setCells(newCharactersSet);
            newWord.setScore(cellService.getScoreByCells(newWord.getCells()));
            newWords.add(newWord);
        }
        newWords.forEach(word -> {
            if (!dictionaryWordService.isAcceptableWord(word.getLetters())) {
                LOGGER.error("The word {} , didn't add", word.getLetters());
                throw new NotAcceptableWordException("Acceptable Word not found");
            }
            word.getCells().forEach(cell -> cell.setBoard(board));
        });
        return newWords;
    }

    private List<Word> getAdjacentWords(List<Cell> newCharacters, Cell[][] characters, DirectionType directionType) {
        List<Word> newWords = new ArrayList<>();
        newCharacters.forEach(cell -> {
            List<Cell> cells = new ArrayList<>();
            if (directionType == DirectionType.VERTICAL) {
                List<Cell> leftCells = cellService.getLeftFillCells(cell, characters);
                List<Cell> rightCells = cellService.getRightFillCells(cell, characters);
                cells.addAll(leftCells);
                cells.addAll(rightCells);
                if (!leftCells.isEmpty() || !rightCells.isEmpty()) {
                    newWords.add(this.createWordByCells(cells, DirectionType.HORIZONTAL));
                }
            } else {
                List<Cell> upCells = cellService.getUpFillCells(cell, characters);
                List<Cell> downCells = cellService.getDownFillCells(cell, characters);
                cells.addAll(upCells);
                cells.addAll(downCells);
                if (!upCells.isEmpty() || !downCells.isEmpty()) {
                    newWords.add(this.createWordByCells(cells, DirectionType.VERTICAL));
                }
            }
        });
        return newWords;
    }

    private Word initializeNewWord(final List<Cell> newCharacters, final Cell[][] characters, final DirectionType directionType) {
        List<Cell> commonCells = cellService.getCommonCells(newCharacters, characters);
        if (commonCells.isEmpty() || commonCells.size() == newCharacters.size()) {
            LOGGER.error("Word coordinates not acceptable  ");
            throw new WordCoordinatesNotAcceptableException("Word coordinates not acceptable");
        }
        List<Cell> cells = new ArrayList<>();
        if (directionType == DirectionType.VERTICAL) {
            cells.addAll(cellService.getUpFillCells(newCharacters.get(0), characters));
            cells.addAll(newCharacters);
            cells.addAll(cellService.getDownFillCells(newCharacters.get(newCharacters.size() - 1), characters));
        } else {
            cells.addAll(cellService.getLeftFillCells(newCharacters.get(0), characters));
            cells.addAll(newCharacters);
            cells.addAll(cellService.getRightFillCells(newCharacters.get(newCharacters.size() - 1), characters));
        }
        return this.createWordByCells(cells, directionType);
    }

    private Word createWordByCells(final List<Cell> cells, final DirectionType directionType) {
        Word word = new Word();
        StringBuilder wordBuilder = new StringBuilder();
        wordBuilder.append(cellService.cellsToString(cells));
        word.setHorizontalStartingPoint(cells.get(0).getxPosition());
        word.setVerticalStartingPoint(cells.get(0).getyPosition());
        word.getCells().addAll(cells);
        word.setLetters(wordBuilder.toString());
        word.setScore(cellService.getScoreByCells(word.getCells()));
        word.setDirectionType(directionType);
        return word;
    }
}
