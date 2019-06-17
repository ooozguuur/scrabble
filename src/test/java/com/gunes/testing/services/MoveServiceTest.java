package com.gunes.testing.services;

import com.gunes.dao.MoveDao;
import com.gunes.enums.DirectionType;
import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Move;
import com.gunes.model.entity.Word;
import com.gunes.service.WordService;
import com.gunes.service.impl.MoveServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoveServiceTest {

    @Mock
    private MoveDao moveDao;

    @Mock
    private WordService wordService;

    @InjectMocks
    private MoveServiceImpl moveService;


    @Test
    public void test_first_move_by_board_success() {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Move move = new Move();
        Mockito.when(moveDao.createEntityObject()).thenReturn(move);
        Mockito.when(moveDao.persist(move)).thenReturn(move);
        move = moveService.firstMoveByBoard(board);
        assertNotNull(move.getBoard());

    }

    @Test
    public void test_get_board_is_content_empty() {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Mockito.when(moveDao.getBoardContent(board.getId(), 1)).thenReturn(null);
        List<Move> moveList = moveService.getBoardContent(board, 1);
        assertNull(moveList);

    }

    @Test
    public void test_play_add_new_move_success() {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);

        Move move = new Move();
        move.setWords(new HashSet<>());
        move.setBoard(board);

        Word word = new Word();
        word.setDirectionType(DirectionType.HORIZONTAL);
        word.setLetters("araba");
        word.setVerticalStartingPoint(5);
        word.setHorizontalStartingPoint(4);
        word.setCells(new HashSet<>());
        move.getWords().add(word);

        Mockito.when(moveDao.getLastSequenceByBoardId(board.getId())).thenReturn(1);

        when(wordService.createWordsByBoard(board, word)).thenAnswer(i -> {
            Set<Word> words = new HashSet<>();
            Word moWord = new Word();
            moWord.setDirectionType(DirectionType.HORIZONTAL);
            moWord.setLetters("araba");
            moWord.setVerticalStartingPoint(5);
            moWord.setHorizontalStartingPoint(4);
            moWord.setCells(new HashSet<>());

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
            cell5.setCharacter('b');

            moWord.getCells().add(cell1);
            moWord.getCells().add(cell2);
            moWord.getCells().add(cell3);
            moWord.getCells().add(cell4);
            moWord.getCells().add(cell5);
            words.add(moWord);
            return words;
        });
        Mockito.when(moveDao.persist(move)).thenReturn(move);
        moveService.play(board, move);
        assertEquals(move.getSequence(), 2);
    }

}
