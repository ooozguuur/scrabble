package com.gunes.controller;

import com.gunes.enums.Status;
import com.gunes.exceptions.BoardNotFoundException;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/board", consumes = "application/json", produces = "application/json")
public class BoardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    private final BoardService boardService;

    public BoardController(final BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createBoard() {
        Board board = boardService.createBoard();
        LOGGER.info("Board created {}", board.getId());
        return ResponseEntity.ok().body(board.getId());
    }

    @PutMapping("/update-status/{boardId}")
    public ResponseEntity<Board> setStatus(@PathVariable Long boardId, @RequestParam String status) {
        if (!Status.isValidStatus(status)) {
            LOGGER.error("The 'status' parameter  not acceptable {}", status);
            throw new IllegalArgumentException("The 'status' parameter  not acceptable ");
        }
        Board board = boardService.getById(boardId);
        if (board == null) {
            LOGGER.error("Board not found. {}", boardId);
            throw new BoardNotFoundException("Board not found. Id:{} " + boardId);
        }
        board.setStatus(Status.valueOf(status));
        board = boardService.update(board);
        LOGGER.info("Board updated status. Id:{}", board.getId());
        return ResponseEntity.ok().body(board);
    }
}
