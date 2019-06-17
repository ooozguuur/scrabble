package com.gunes.testing.controller;

import com.gunes.controller.BoardController;
import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardControllerTest {

    @InjectMocks
    private BoardController boardController;

    @Mock
    private BoardService boardService;


    private MockMvc mockMvc;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    public void test_create_board_success() throws Exception {
        Mockito.when(boardService.createBoard()).thenReturn(new Board(15, 15));
        this.mockMvc.perform(post("/board/create")).andExpect(status().isOk());
    }

    @Test
    public void test_update_board_status_success() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);
        Mockito.when(boardService.findById(1L)).thenReturn(board);
        Mockito.when(boardService.update(board)).thenReturn(board);
        MvcResult mvcResult = this.mockMvc.perform(
                put("/board/update-status/{boardId}", 1L).param("status", Status.PASSIVE.toString()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("PASSIVE"));
    }

    @Test
    public void test_update_status_not_acceptable_board() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.PASSIVE);
        Mockito.when(boardService.findById(1L)).thenReturn(board);
        this.mockMvc.perform(put("/board/update-status/{boardId}", 1L).param("status", Status.ACTIVE.toString())).andExpect(status().is(500));
    }

    @Test
    public void test_update_status_board_not_found_exception() throws Exception {
        Mockito.when(boardService.findById(1L)).thenReturn(null);
        this.mockMvc.perform(put("/board/update-status/{boardId}", 1L).param("status", Status.ACTIVE.toString())).andExpect(status().is(404));
    }
}
