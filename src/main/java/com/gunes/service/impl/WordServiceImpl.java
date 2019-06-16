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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordServiceImpl extends GenericServiceImpl<Word, Long> implements WordService {

    private static final int DEFAULT_HORIZONTAL_SIZE = 15;

    private static final int DEFAULT_VERTICAL_SIZE = 15;

    @Autowired
    private CellService cellService;

    private WordDao wordDao;

    @Autowired
    public WordServiceImpl(final WordDao wordDao) {
        super(wordDao);
        this.wordDao = wordDao;
    }

    @Override
    public List<Word> getWords(final Long boardId) {
        return  wordDao.getWords(boardId);
    }

    @Override
    public List<Word> createWordsByBoard(final Board board, Word word) {
        if (!isAvailableCoordinates(board.getHorizontalSize(), board.getVerticalSize(), word)) {
            //TODO ERROR
        }
        List<Word> words = new ArrayList<>();
        List<Cell> letters = cellService.splitTheWord(word);
        letters.forEach(l -> l.setBoard(board));
        Set<Cell> letterSet = new HashSet<>(letters);
        List<Cell> addedLetters = cellService.getByBoardId(board.getId());
        Character[][] characters = listToArray(addedLetters);
        if (!addedLetters.isEmpty()) {
            for (final Cell letter : letters) {
                if (characters[letter.getxPosition()][letter.getyPosition()] != null && characters[letter.getxPosition()][letter.getyPosition()] != letter.getCharacter()) {
                    //TODO ERROR
                    System.out.println("ERROR");
                }
            }
             for (final Cell letter : letters) {
                    if (characters[letter.getxPosition()][letter.getyPosition() -1 ] != null) {
                        StringBuilder letterBuilder = new StringBuilder();
                        for (int i = letter.getyPosition() - 1; i == 0 ; i--) {
                            if (characters[letter.getxPosition()][i] != null) {
                                letterBuilder.append(characters[letter.getxPosition()][i]);
                            } else {
                                break;
                            }
                        }
                        System.out.println(letterBuilder.toString());
                    }

                 if (characters[letter.getxPosition()][letter.getyPosition() + 1 ] != null) {
                     StringBuilder letterBuilder = new StringBuilder();
                     for (int i = letter.getyPosition() + 1; i == DEFAULT_HORIZONTAL_SIZE ; i++) {
                         if (characters[letter.getxPosition()][i] != null) {
                             letterBuilder.append(characters[letter.getxPosition()][i]);
                         } else {
                             break;
                         }
                     }
                     System.out.println(letterBuilder.toString());
                 }

                 if (characters[letter.getxPosition() -1][letter.getyPosition()] != null) {
                     StringBuilder letterBuilder = new StringBuilder();
                     for (int i = letter.getxPosition() - 1; i == 0 ; i--) {
                         if (characters[letter.getxPosition()][i] != null) {
                             letterBuilder.append(characters[i][letter.getxPosition()]);
                         } else {
                             break;
                         }
                     }
                     System.out.println(letterBuilder.toString());
                 }

                 if (characters[letter.getxPosition() + 1 ][letter.getyPosition()] != null) {
                     StringBuilder letterBuilder = new StringBuilder();
                     for (int i = letter.getxPosition() + 1; i == DEFAULT_VERTICAL_SIZE ; i++) {
                         if (characters[letter.getxPosition()][i] != null) {
                             letterBuilder.append(characters[i][letter.getxPosition()]);
                         } else {
                             break;
                         }
                     }
                     System.out.println(letterBuilder.toString());
                 }
             }
        }
        word.setCells(letterSet);
        words.add(word);
        wordDao.persist(word);
        return words;
    }

    private Character[][] listToArray(List<Cell> cells) {
        Character[][] cellArray = new Character[DEFAULT_HORIZONTAL_SIZE][DEFAULT_VERTICAL_SIZE];
        cells.forEach(cell -> cellArray[cell.getxPosition()][cell.getyPosition()] = cell.getCharacter());
        return cellArray;
    }
}
