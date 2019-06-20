package com.gunes.testing.services;

import com.gunes.dao.WordDao;
import com.gunes.enums.DirectionType;
import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.CellService;
import com.gunes.service.DictionaryWordService;
import com.gunes.service.impl.WordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WordServiceTest {

    @Mock
    private CellService cellService;

    @Mock
    private DictionaryWordService dictionaryWordService;

    @Mock
    private WordDao wordDao;

    @InjectMocks
    private WordServiceImpl wordService;

    @Test
    public void test_get_board_is_content_empty() {
        Mockito.when(wordDao.getWords(1L)).thenReturn(null);
        List<Word> words = wordService.getWords(1L);
        assertNull(words);
    }

    public void test_create_word_by_board_success_create_words() {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Word word = new Word();
        word.setDirectionType(DirectionType.HORIZONTAL);
        word.setLetters("cop");
        word.setVerticalStartingPoint(5);
        word.setHorizontalStartingPoint(4);
        word.setCells(new HashSet<>());

        List<Cell> cells = new ArrayList<>();
        Cell cell1 = new Cell();
        cell1.setxPosition(5);
        cell1.setyPosition(4);
        cell1.setCharacter('c');

        Cell cell2 = new Cell();
        cell2.setxPosition(5);
        cell2.setyPosition(5);
        cell2.setCharacter('o');

        Cell cell3 = new Cell();
        cell3.setxPosition(5);
        cell3.setyPosition(6);
        cell3.setCharacter('p');

        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);

        Mockito.when(cellService.getByBoardId(board.getId())).thenReturn(new ArrayList());
        Mockito.when(cellService.splitTheWord(word)).thenReturn(cells);
        Mockito.when(dictionaryWordService.isAcceptableWord(word.getLetters())).thenReturn(true);

        Set<Word> words = wordService.createWordsByBoard(board, word);
        assertEquals(words.size(), 1);
        assertEquals(word.getScore(), 11);
    }

}
