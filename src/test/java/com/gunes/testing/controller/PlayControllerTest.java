package com.gunes.testing.controller;

import com.gunes.controller.PlayController;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.HashSet;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayControllerTest {

    @InjectMocks
    private PlayController playController;

    @Mock
    private BoardService boardService;

    @Mock
    private MoveService moveService;

    private MockMvc mockMvc;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(playController).build();
    }


    @Test
    public void shouldAddWordWhenBoardNotFoundException() throws Exception {
        Mockito.when(boardService.findById(1L)).thenReturn(null);
        this.mockMvc.perform(post("/play/{boardId}", 1L).contentType(MediaType.APPLICATION_JSON)
                                     .content("{\n" +
                                                      "  \"words\": [\n" +
                                                      "    {\n" +
                                                      "    \"letters\": \"cop\",\n" +
                                                      "    \"horizontalStartingPoint\": 5,\n" +
                                                      "    \"verticalStartingPoint\": 4,\n" +
                                                      "    \"directionType\": \"HORIZONTAL\"\n" +
                                                      "  }]\n" +
                                                      "}"))
                .andExpect(status().is(404));
    }

    @Test
    public void shouldAddWordWhenBoardWhenNotAcceptable() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.PASSIVE);
        Mockito.when(boardService.findById(1L)).thenReturn(board);
        this.mockMvc.perform(post("/play/{boardId}", 1L).contentType(MediaType.APPLICATION_JSON)
                                     .content("{\n" +
                                                      "  \"words\": [\n" +
                                                      "    {\n" +
                                                      "    \"letters\": \"cop\",\n" +
                                                      "    \"horizontalStartingPoint\": 5,\n" +
                                                      "    \"verticalStartingPoint\": 4,\n" +
                                                      "    \"directionType\": \"HORIZONTAL\"\n" +
                                                      "  }]\n" +
                                                      "}"))
                .andExpect(status().is(404));
    }

    @Test
    public void shouldAddWordWhenSuccess() throws Exception {
        Board board = new Board(15, 15);
        board.setId(1L);
        board.setStatus(Status.ACTIVE);

        Move move = new Move();
        move.setSequence(1);
        move.setWords(new HashSet<>());
        move.setBoard(board);

        Word word = new Word();
        word.setDirectionType(DirectionType.HORIZONTAL);
        word.setLetters("cop");
        word.setVerticalStartingPoint(5);
        word.setHorizontalStartingPoint(4);
        word.setCells(new HashSet<>());

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


        word.getCells().add(cell1);
        word.getCells().add(cell2);
        word.getCells().add(cell3);

        move.getWords().add(word);
        Mockito.when(boardService.findById(1L)).thenReturn(board);
        Mockito.when(moveService.play(board, move)).thenReturn(move);
        this.mockMvc.perform(post("/play/{boardId}", 1L).contentType(MediaType.APPLICATION_JSON)
                                     .content("{\n" +
                                                      "  \"words\": [\n" +
                                                      "    {\n" +
                                                      "    \"letters\": \"cop\",\n" +
                                                      "    \"horizontalStartingPoint\": 5,\n" +
                                                      "    \"verticalStartingPoint\": 4,\n" +
                                                      "    \"directionType\": \"HORIZONTAL\"\n" +
                                                      "  }]\n" +
                                                      "}"))
                .andExpect(status().isOk());
    }
}
