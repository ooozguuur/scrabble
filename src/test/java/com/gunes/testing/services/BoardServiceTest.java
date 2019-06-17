package com.gunes.testing.services;

import com.gunes.dao.BoardDao;
import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.service.MoveService;
import com.gunes.service.impl.BoardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private MoveService moveService;

    @Mock
    private BoardDao boardDao;

    @InjectMocks
    private BoardServiceImpl boardService;


    @Test
    public void test_create_board_success() {
        Board board = new Board(15, 15);
        Move move = new Move();
        move.setBoard(board);
        move.setSequence(0);
        move.setWords(new HashSet<>());
        Mockito.when(boardDao.createEntityObject()).thenReturn(board);
        Mockito.when(boardDao.persist(board)).thenReturn(board);
        Mockito.when(moveService.firstMoveByBoard(board)).thenReturn(move);
        board = boardService.createBoard();
        assertEquals(board.getStatus(), Status.ACTIVE);
    }
}
