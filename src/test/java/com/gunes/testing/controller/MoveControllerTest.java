package com.gunes.testing.controller;

import com.gunes.controller.MoveController;
import com.gunes.enums.DirectionType;
import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Move;
import com.gunes.model.entity.Word;
import com.gunes.service.BoardService;
import com.gunes.service.MoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MoveControllerTest {

    @InjectMocks
    private MoveController moveController;

    @Mock
    private BoardService boardService;

    @Mock
    private MoveService moveService;

    private MockMvc mockMvc;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(moveController).build();
    }

    @Test
    public void shouldGetBoardContentWhenContentIsEmpty() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Mockito.when(boardService.findById(1L)).thenReturn(board);
        Mockito.when(moveService.getBoardContent(board, 0)).thenReturn(new ArrayList<>());
        MvcResult mvcResult = this.mockMvc.perform(get("/move/get-board-content/{boardId}/{sequence}", 1L, 0L))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(), "[]");
    }

    @Test
    public void shouldGetBoardContentWhenBoardNotFoundException() throws Exception {
        Mockito.when(boardService.findById(1L)).thenReturn(null);
       this.mockMvc.perform(get("/move/get-board-content/{boardId}/{sequence}", 1L, 0L)).andExpect(status().is(404));
    }

    @Test
    public void shouldGetBoardContentWhenFirstSequence() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        List<Move> moves = new ArrayList<>();
        Move move = new Move();
        move.setSequence(0);
        move.setWords(new HashSet<>());
        move.setBoard(board);

        Move move1 = new Move();
        move1.setSequence(1);
        move1.setWords(new HashSet<>());
        move1.setBoard(board);

        Word word = new Word();
        word.setDirectionType(DirectionType.HORIZONTAL);
        word.setLetters("araba");
        word.setVerticalStartingPoint(5);
        word.setHorizontalStartingPoint(4);
        word.setCells(new HashSet<>());

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

        word.getCells().add(cell1);
        word.getCells().add(cell2);
        word.getCells().add(cell3);
        word.getCells().add(cell4);
        word.getCells().add(cell5);

        move1.getWords().add(word);
        moves.add(move);
        moves.add(move1);
        Mockito.when(boardService.findById(1L)).thenReturn(board);
        Mockito.when(moveService.getBoardContent(board, 1)).thenReturn(moves);
        MvcResult mvcResult = this.mockMvc.perform(get("/move/get-board-content/{boardId}/{sequence}", 1L, 1L))
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(mvcResult.getResponse().getContentAsString().contains("[]"));
    }
}
