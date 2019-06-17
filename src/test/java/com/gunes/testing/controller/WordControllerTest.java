package com.gunes.testing.controller;

import com.gunes.controller.WordController;
import com.gunes.enums.DirectionType;
import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Word;
import com.gunes.service.BoardService;
import com.gunes.service.WordService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WordControllerTest {

    @InjectMocks
    private WordController wordController;

    @Mock
    private BoardService boardService;

    @Mock
    private WordService wordService;

    private MockMvc mockMvc;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(wordController).build();
    }

    @Test
    public void shouldGetWordsWhenWordIsEmpty() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Mockito.when(boardService.findById(1L)).thenReturn(board);
        Mockito.when(wordService.getWords(1L)).thenReturn(new ArrayList<>());
        MvcResult mvcResult = this.mockMvc.perform(get("/word/get-words/{boardId}", 1L)).andExpect(
                status().isOk()).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(), "[]");
    }

    @Test
    public void shouldGetWordsWhenBoardNotFoundException() throws Exception {
        Mockito.when(boardService.findById(1L)).thenReturn(null);
        this.mockMvc.perform(get("/word/get-words/{boardId}", 1L)).andExpect(status().is(404));
    }

    @Test
    public void shouldGetWordsWhenSuccess() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);

        List<Word> words = new ArrayList<>();
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
        words.add(word);

        Mockito.when(boardService.findById(1L)).thenReturn(board);
        Mockito.when(wordService.getWords(1L)).thenReturn(words);
        MvcResult mvcResult = this.mockMvc.perform(get("/word/get-words/{boardId}", 1L)).andExpect(
                status().isOk())
                .andReturn();
        assertFalse(mvcResult.getResponse().getContentAsString().contains("[]"));
    }
}
