package com.gunes.testing.services;

import com.gunes.dao.CellDao;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CellServiceTest {

    @Mock
    private CellDao cellDao;

    @InjectMocks
    private CellServiceImpl cellService;

    @Test
    public void shouldSplitTheWordWhenSuccess() {
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
    public void shouldSplitTheWordWhenNullPointerException() {
        Word word = new Word();
        Assertions.assertThrows(WordNotFoundException.class, () -> cellService.splitTheWord(word));
    }

    @Test
    public void shouldCellToStringWhenReturnString() {
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
        String letters = cellService.cellToString(cells);
        assertEquals(letters, "araba");
    }

    @Test
    public void shouldCellToStringWhenNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> cellService.cellToString(null));
    }

}
