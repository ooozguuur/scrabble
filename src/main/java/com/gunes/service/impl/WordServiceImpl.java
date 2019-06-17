package com.gunes.service.impl;

import com.gunes.dao.WordDao;
import com.gunes.enums.CharacterScore;
import com.gunes.enums.DirectionType;
import com.gunes.exceptions.CellCharacterUpdatedException;
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

import java.util.*;

@Service
public class WordServiceImpl extends GenericServiceImpl<Word, Long> implements WordService {

    private static final int DEFAULT_HORIZONTAL_SIZE = 15;

    private static final int DEFAULT_VERTICAL_SIZE = 15;

    private static final Logger LOGGER = LoggerFactory.getLogger(WordServiceImpl.class);

    @Autowired
    private CellService cellService;

    @Autowired
    private DictionaryWordService dictionaryWordService;

    private WordDao wordDao;

    @Autowired
    public WordServiceImpl(final WordDao wordDao) {
        super(wordDao);
        this.wordDao = wordDao;
    }

    @Override
    public List<Word> getWords(final Long boardId) {
        return wordDao.getWords(boardId);
    }

    @Override
    public List<Word> createWordsByBoard(final Board board, Word word) {
        if (!isAvailableCoordinates(board.getHorizontalSize(), board.getVerticalSize(), word)) {
            LOGGER.error("Word coordinates not acceptable : Horizontal {} Vertical {}", word.getHorizontalStartingPoint(), word.getVerticalStartingPoint());
            throw new WordCoordinatesNotAcceptableException("Word coordinates not acceptable");
        }
        List<Word> words = getAcceptableWords(board, word);
        if (words.isEmpty()) {
            LOGGER.error("The word {} , didn't add", word.getLetters());
            throw new NotAcceptableWordException("Acceptable Word not found");
        }
        words.forEach(this::setScoreByWord);
        words.forEach(this::persist);
        return words;
    }

    private void setScoreByWord(final Word word) {
        int score = 0;
        for (Cell cell : word.getCells()) {
            score += CharacterScore.getScore(cell.getCharacter());
            word.setScore(score);
        }
    }

    private List<Word> getAcceptableWords(Board board, Word word) {
        List<Cell> addedCharacters = cellService.getByBoardId(board.getId());
        List<Cell> newCharacters = cellService.splitTheWord(word);
        List<Word> words = new ArrayList<>();
        if (!addedCharacters.isEmpty()) {
            Cell[][] characters = listToArray(addedCharacters);
            checkCellsInCharacter(newCharacters, characters);
            for (final Cell letter : newCharacters) {
                Word horizontalWord = getHorizontalWord(characters, letter);
                Word verticalWord = getVerticalWord(characters, letter);
                words.add(horizontalWord);
                words.add(verticalWord);
            }
        } else {
            if (!dictionaryWordService.isAcceptableWord(word.getLetters())) {
                LOGGER.error("The word {} , didn't add", word.getLetters());
                throw new NotAcceptableWordException("Acceptable Word not found");
            }
            newCharacters.forEach(cell -> cell.setBoard(board));
            Set<Cell> newCharactersSet = new HashSet<>(newCharacters);
            word.setCells(newCharactersSet);
            words.add(word);
        }
        return words;
    }


    private Word getHorizontalWord(final Cell[][] characters, final Cell letter) {
        List<Cell> cells = new ArrayList<>();
        if (characters[letter.getxPosition()][letter.getyPosition() - 1] != null) {
            for (int i = letter.getyPosition() - 1; i == 0; i--) {
                if (characters[letter.getxPosition()][i] != null) {
                    cells.add(characters[letter.getxPosition()][i]);
                } else {
                    break;
                }
            }
            cells.sort(Collections.reverseOrder());
        }
        if (characters[letter.getxPosition()][letter.getyPosition() + 1] != null) {
            for (int i = letter.getyPosition(); i == DEFAULT_VERTICAL_SIZE; i++) {
                if (characters[letter.getxPosition()][i] != null) {
                    cells.add(characters[letter.getxPosition()][i]);
                } else {
                    break;
                }
            }
        }
        if (cells.isEmpty()) {
            return null;
        }
        String letters = cellService.cellToString(cells);
        if (!dictionaryWordService.isAcceptableWord(letters)) {
            LOGGER.error("The word {} , didn't add", letters);
            throw new NotAcceptableWordException("Acceptable Word not found");
        }
        Word word = this.createEntityObject();
        word.setDirectionType(DirectionType.VERTICAL);
        Set<Cell> cellSet = new HashSet<>(cells);
        word.setCells(cellSet);
        word.setHorizontalStartingPoint(letter.getxPosition());
        word.setVerticalStartingPoint(cells.get(0).getyPosition());
        word.setLetters(letters);
        return word;
    }

    private Word getVerticalWord(final Cell[][] characters, final Cell letter) {
        List<Cell> cells = new ArrayList<>();
        if (characters[letter.getxPosition() - 1][letter.getyPosition()] != null) {
            for (int i = letter.getxPosition() - 1; i == 0; i--) {
                if (characters[i][letter.getyPosition()] != null) {
                    cells.add(characters[i][letter.getyPosition()]);
                } else {
                    break;
                }
            }
            cells.sort(Collections.reverseOrder());
        }
        if (characters[letter.getxPosition() + 1][letter.getyPosition()] != null) {
            for (int i = letter.getxPosition(); i == DEFAULT_HORIZONTAL_SIZE; i++) {
                if (characters[i][letter.getyPosition()] != null) {
                    cells.add(characters[i][letter.getyPosition()]);
                } else {
                    break;
                }
            }
        }
        if (cells.isEmpty()) {
            return null;
        }
        String letters = cellService.cellToString(cells);
        if (!dictionaryWordService.isAcceptableWord(letters)) {
            LOGGER.error("The word {} , didn't add", letters);
            throw new NotAcceptableWordException("Acceptable Word not found");
        }
        Word word = this.createEntityObject();
        word.setDirectionType(DirectionType.HORIZONTAL);
        Set<Cell> cellSet = new HashSet<>(cells);
        word.setCells(cellSet);
        word.setHorizontalStartingPoint(cells.get(0).getxPosition());
        word.setVerticalStartingPoint(letter.getyPosition());
        word.setLetters(cellService.cellToString(cells));
        return word;
    }

    private void checkCellsInCharacter(final List<Cell> letters, final Cell[][] cells) {
        for (final Cell letter : letters) {
            if (cells[letter.getxPosition()][letter.getyPosition()] != null && cells[letter.getxPosition()][letter.getyPosition()].getCharacter() != letter.getCharacter()) {
                LOGGER.error("Can not change cell in character.");
                throw new CellCharacterUpdatedException("Can not change cell in character");
            }
        }
    }

    private Cell[][] listToArray(List<Cell> cells) {
        Cell[][] cellArray = new Cell[DEFAULT_HORIZONTAL_SIZE][DEFAULT_VERTICAL_SIZE];
        cells.forEach(cell -> cellArray[cell.getxPosition()][cell.getyPosition()] = cell);
        return cellArray;
    }
}
