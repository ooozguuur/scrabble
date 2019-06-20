package com.gunes.testing.services;

import com.gunes.dao.CellDao;
import com.gunes.enums.DirectionType;
import com.gunes.exceptions.WordNotFoundException;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.impl.CellServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CellServiceTest {

    @Mock
    private CellDao cellDao;

    @InjectMocks
    private CellServiceImpl cellService;

    @Test
    public void test_split_vertical_word_success_result() {
        Word word = new Word();
        word.setHorizontalStartingPoint(5);
        word.setHorizontalStartingPoint(4);
        word.setLetters("cop");
        when(cellDao.createEntityObject()).thenAnswer(i -> {
            Cell cell = new Cell();
            return cell;
        });
        List<Cell> cells = cellService.splitTheWord(word);
        assertEquals(cells.size(), 3);
    }

    @Test
    public void test_split_horizontal_word_success_result() {
        Word word = new Word();
        word.setHorizontalStartingPoint(5);
        word.setHorizontalStartingPoint(4);
        word.setDirectionType(DirectionType.HORIZONTAL);
        word.setLetters("cop");
        when(cellDao.createEntityObject()).thenAnswer(i -> {
            Cell cell = new Cell();
            return cell;
        });
        List<Cell> cells = cellService.splitTheWord(word);
        assertEquals(cells.size(), 3);
    }

    @Test
    public void test_list_to_array_success_result() {
        List<Cell> cells = new ArrayList<>();
        Cell cell1 = new Cell();
        cell1.setxPosition(5);
        cell1.setyPosition(4);
        cell1.setCharacter('a');

        Cell cell2 = new Cell();
        cell2.setxPosition(5);
        cell2.setyPosition(5);
        cell2.setCharacter('r');

        Cell cell3 = new Cell();
        cell3.setxPosition(5);
        cell3.setyPosition(6);
        cell3.setCharacter('a');

        Cell cell4 = new Cell();
        cell4.setxPosition(5);
        cell4.setyPosition(7);
        cell4.setCharacter('b');

        Cell cell5 = new Cell();
        cell5.setxPosition(5);
        cell5.setyPosition(8);
        cell5.setCharacter('a');
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        cells.add(cell4);
        cells.add(cell5);
        Cell[][] cellArray = cellService.listToArray(cells);
        assertEquals(cellArray[5][4].getCharacter(), 'a');
        assertEquals(cellArray[5][5].getCharacter(), 'r');
        assertEquals(cellArray[5][6].getCharacter(), 'a');
        assertEquals(cellArray[5][7].getCharacter(), 'b');
        assertEquals(cellArray[5][8].getCharacter(), 'a');
    }

    @Test
    public void test_split_word_null_pointer_exception() {
        Word word = new Word();
        Assertions.assertThrows(WordNotFoundException.class, () -> cellService.splitTheWord(word));
    }

    @Test
    public void test_cell_to_string_success_string() {
        List<Cell> cells = new ArrayList<>();
        Cell cell1 = new Cell();
        cell1.setxPosition(5);
        cell1.setyPosition(4);
        cell1.setCharacter('a');

        Cell cell2 = new Cell();
        cell2.setxPosition(5);
        cell2.setyPosition(5);
        cell2.setCharacter('r');

        Cell cell3 = new Cell();
        cell3.setxPosition(5);
        cell3.setyPosition(6);
        cell3.setCharacter('a');

        Cell cell4 = new Cell();
        cell4.setxPosition(5);
        cell4.setyPosition(7);
        cell4.setCharacter('b');

        Cell cell5 = new Cell();
        cell5.setxPosition(5);
        cell5.setyPosition(8);
        cell5.setCharacter('a');
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        cells.add(cell4);
        cells.add(cell5);
        String letters = cellService.cellsToString(cells);
        assertEquals(letters, "araba");
    }

    @Test
    public void test_cell_to_string_cell_is_empty() {
        assertEquals("", cellService.cellsToString(null));
    }


    @Test
    public void test_get_up_fill_cells_success() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[5][3] = new Cell();
        characters[5][3].setCharacter('b');

        List<Cell> cells = cellService.getUpFillCells(cell, characters);
        assertEquals(cells.get(0).getCharacter(), 'b');
    }

    @Test
    public void test_get_up_fill_cells_empty_result() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[5][2] = new Cell();
        characters[5][2].setCharacter('b');

        List<Cell> cells = cellService.getUpFillCells(cell, characters);
        assertTrue(cells.isEmpty());
    }


    @Test
    public void test_get_down_fill_cells_success() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[5][5] = new Cell();
        characters[5][5].setCharacter('b');

        List<Cell> cells = cellService.getDownFillCells(cell, characters);
        assertEquals(cells.get(0).getCharacter(), 'b');
    }

    @Test
    public void test_get_down_fill_cells_empty_result() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[5][7] = new Cell();
        characters[5][7].setCharacter('b');

        List<Cell> cells = cellService.getUpFillCells(cell, characters);
        assertTrue(cells.isEmpty());
    }


    @Test
    public void test_get_left_fill_cells_success() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[4][4] = new Cell();
        characters[4][4].setCharacter('b');

        List<Cell> cells = cellService.getLeftFillCells(cell, characters);
        assertEquals(cells.get(0).getCharacter(), 'b');
    }

    @Test
    public void test_get_left_fill_cells_empty_result() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[3][4] = new Cell();
        characters[3][4].setCharacter('b');

        List<Cell> cells = cellService.getLeftFillCells(cell, characters);
        assertTrue(cells.isEmpty());
    }

    @Test
    public void test_get_right_fill_cells_success() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[6][4] = new Cell();
        characters[6][4].setCharacter('b');

        List<Cell> cells = cellService.getRightFillCells(cell, characters);
        assertEquals(cells.get(0).getCharacter(), 'b');
    }

    @Test
    public void test_get_right_fill_cells_empty_result() {
        Cell cell = new Cell();
        cell.setxPosition(5);
        cell.setyPosition(4);
        cell.setCharacter('a');

        Cell[][] characters = new Cell[15][15];
        characters[7][4] = new Cell();
        characters[7][4].setCharacter('b');

        List<Cell> cells = cellService.getRightFillCells(cell, characters);
        assertTrue(cells.isEmpty());
    }
}
