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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    public void test_create_first_word_by_board_success_create_words() {
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
        Mockito.when(cellService.getScoreByCells(new HashSet<>(cells))).thenReturn(11);
        Mockito.when(dictionaryWordService.isAcceptableWord(word.getLetters())).thenReturn(true);

        Set<Word> words = wordService.createWordsByBoard(board, word);
        assertEquals(words.size(), 1);
        assertEquals(word.getScore(), 11);
    }

    @Test
    public void test_create_second_word_by_board_success_create_words() {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Word word = new Word();
        word.setDirectionType(DirectionType.HORIZONTAL);
        word.setLetters("masacı");
        word.setVerticalStartingPoint(5);
        word.setHorizontalStartingPoint(4);
        word.setCells(new HashSet<>());

        List<Cell> addedCells = new ArrayList<>();
        Cell cell1 = new Cell();
        cell1.setxPosition(5);
        cell1.setyPosition(4);
        cell1.setCharacter('m');

        Cell cell2 = new Cell();
        cell2.setxPosition(5);
        cell2.setyPosition(5);
        cell2.setCharacter('a');

        Cell cell3 = new Cell();
        cell3.setxPosition(5);
        cell3.setyPosition(6);
        cell3.setCharacter('s');

        Cell cell4 = new Cell();
        cell3.setxPosition(5);
        cell3.setyPosition(7);
        cell3.setCharacter('a');


        addedCells.add(cell1);
        addedCells.add(cell2);
        addedCells.add(cell3);
        addedCells.add(cell4);

        List<Cell> newCells = new ArrayList<>();

        Cell newCell1 = new Cell();
        newCell1.setxPosition(5);
        newCell1.setyPosition(4);
        newCell1.setCharacter('m');

        Cell newCell2 = new Cell();
        newCell2.setxPosition(5);
        newCell2.setyPosition(5);
        newCell2.setCharacter('a');

        Cell newCell3 = new Cell();
        newCell3.setxPosition(5);
        newCell3.setyPosition(6);
        newCell3.setCharacter('s');

        Cell newCell4 = new Cell();
        newCell4.setxPosition(5);
        newCell4.setyPosition(7);
        newCell4.setCharacter('a');

        Cell newCell5 = new Cell();
        newCell5.setxPosition(5);
        newCell5.setyPosition(8);
        newCell5.setCharacter('c');

        Cell newCell6 = new Cell();
        newCell6.setxPosition(5);
        newCell6.setyPosition(9);
        newCell6.setCharacter('ı');

        newCells.add(newCell1);
        newCells.add(newCell2);
        newCells.add(newCell3);
        newCells.add(newCell4);
        newCells.add(newCell5);
        newCells.add(newCell6);


        Cell[][] characters = new Cell[15][15];
        characters[5][4] = new Cell();
        characters[5][4].setCharacter('m');
        characters[5][5] = new Cell();
        characters[5][5].setCharacter('a');
        characters[5][6] = new Cell();
        characters[5][6].setCharacter('s');
        characters[5][7] = new Cell();
        characters[5][7].setCharacter('a');

        Mockito.when(cellService.getByBoardId(board.getId())).thenReturn(addedCells);
        Mockito.when(cellService.splitTheWord(word)).thenReturn(newCells);
        Mockito.when(cellService.listToArray(addedCells)).thenReturn(characters);
        Mockito.when(cellService.getCommonCells(newCells, characters)).thenReturn(addedCells);
        Mockito.when(cellService.cellsToString(newCells)).thenReturn("masacı");
        Mockito.when(cellService.getScoreByCells(new HashSet<>(newCells))).thenReturn(12);
        Mockito.when(dictionaryWordService.isAcceptableWord(word.getLetters())).thenReturn(true);
        Set<Word> words = wordService.createWordsByBoard(board, word);
        assertEquals(words.size(), 1);
        assertEquals(((Word) words.toArray()[0]).getScore(), 12);
    }

}
