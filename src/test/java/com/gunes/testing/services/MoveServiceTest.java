package com.gunes.testing.services;

import com.gunes.dao.MoveDao;
import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.service.WordService;
import com.gunes.service.impl.MoveServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void test_play_add_new_move_success() {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Move move = new Move();
        Mockito.when(moveDao.createEntityObject()).thenReturn(move);
        Mockito.when(moveDao.persist(move)).thenReturn(move);
        move = moveService.firstMoveByBoard(board);
        assertNotNull(move.getBoard());

    }

}
