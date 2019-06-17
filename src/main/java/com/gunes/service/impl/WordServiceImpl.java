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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordServiceImpl extends GenericServiceImpl<Word, Long> implements WordService {

    private static final int DEFAULT_HORIZONTAL_SIZE = 14;

    private static final int DEFAULT_VERTICAL_SIZE = 14;

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
            throw new WordCoordinatesNotAcceptableException("Word coordinates not acceptable");
        }
        Set<Word> words = getAcceptableWords(board, word);
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

    private Set<Word> getAcceptableWords(Board board, Word word) {
        List<Cell> addedCharacters = cellService.getByBoardId(board.getId());
        List<Cell> newCharacters = cellService.splitTheWord(word);
        Set<Word> words = new HashSet<>();
        if (!addedCharacters.isEmpty()) {
            Cell[][] characters = listToArray(addedCharacters);
            checkCellsInCharacter(newCharacters, characters);
            if (!hasAdjacentCell(newCharacters, characters, word.getDirectionType())) {
                LOGGER.error("The word {} , didn't add", word.getLetters());
                throw new NotAcceptableWordException("Acceptable Word not found");
            }
        }

        if (!dictionaryWordService.isAcceptableWord(word.getLetters())) {
            LOGGER.error("The word {} , didn't add", word.getLetters());
            throw new NotAcceptableWordException("Acceptable Word not found");
        }
        newCharacters.forEach(cell -> cell.setBoard(board));
        Set<Cell> newCharactersSet = new HashSet<>(newCharacters);
        word.setCells(newCharactersSet);
        words.add(word);
        return words;
    }

    private boolean hasAdjacentCell(final List<Cell> newCharacters, final Cell[][] cells, DirectionType directionType) {
        for (final Cell letter : newCharacters) {
            int yBeforePosition = letter.getyPosition() == 0 ? 0 : letter.getyPosition() - 1;
            int xBeforePosition = letter.getxPosition() == 0 ? 0 : letter.getxPosition() - 1;
            int yAfterPosition = letter.getyPosition() == DEFAULT_HORIZONTAL_SIZE ? DEFAULT_HORIZONTAL_SIZE :letter.getyPosition() + 1;
            int xAfterPosition = letter.getxPosition() == DEFAULT_VERTICAL_SIZE ? DEFAULT_VERTICAL_SIZE :letter.getxPosition() + 1;
            switch (directionType) {
                case VERTICAL:
                    if (cells[xBeforePosition][letter.getyPosition()] != null || cells[xAfterPosition][letter.getyPosition()] != null) {
                        return true;
                    }
                    break;
                case HORIZONTAL:
                    if (cells[letter.getxPosition()][yBeforePosition] != null || cells[letter.getxPosition()][yAfterPosition] != null) {
                        return true;
                    }
                    break;
                default:
            }

        }
        return false;
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


    /*
      private List<Cell> getHorizontalCells(final Cell[][] characters, final Cell letter) {
        List<Cell> cells = new ArrayList<>();
        int yPosition = letter.getyPosition() == 0 ? 0 : letter.getyPosition() -1;
        if (characters[letter.getxPosition()][yPosition] != null) {
            for (int i = yPosition - 1; i >= 0; i--) {
                if (characters[letter.getxPosition()][i] != null) {
                    cells.add(characters[letter.getxPosition()][i]);
                } else {
                    break;
                }
            }
            cells = Lists.reverse(cells);
        }
        cells.add(letter);
        if (characters[letter.getxPosition()][letter.getyPosition() + 1] != null) {
            for (int i = letter.getyPosition(); i < DEFAULT_VERTICAL_SIZE; i++) {
                if (characters[letter.getxPosition()][i] != null) {
                    cells.add(characters[letter.getxPosition()][i]);
                } else {
                    break;
                }
            }
        }
        return cells;
    }

    private List<Cell> getVerticalCells(final Cell[][] characters, final Cell letter) {
        List<Cell> cells = new ArrayList<>();
        int xPosition = letter.getxPosition() == 0 ? 0 : letter.getxPosition() -1;
        if (characters[xPosition][letter.getyPosition()] != null) {
            for (int i = xPosition - 1; i >= 0; i--) {
                if (characters[i][letter.getyPosition()] != null) {
                    cells.add(characters[i][letter.getyPosition()]);
                } else {
                    break;
                }
            }
            cells = Lists.reverse(cells);
        }
        cells.add(letter);
        if (characters[letter.getxPosition() + 1][letter.getyPosition()] != null) {
            for (int i = letter.getxPosition(); i < DEFAULT_HORIZONTAL_SIZE; i++) {
                if (characters[i][letter.getyPosition()] != null) {
                    cells.add(characters[i][letter.getyPosition()]);
                } else {
                    break;
                }
            }
        }
        return cells;
    }
     */
}
