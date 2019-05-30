package com.gunes.controller;

import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/board", consumes = "application/json", produces = "application/json")
public class BoardController {

    private final BoardService boardService;

    public BoardController(final BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createBoard() {
        Board board = boardService.createBoard();
        if (board != null) {
            return ResponseEntity.ok().body(board.getId());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/update-status/{boardId}")
    public ResponseEntity<Board> setStatus(@PathVariable Long boardId, @RequestParam String status) {
        if (status == null) {
            //TODO
        }
        Board board = boardService.getById(boardId);
        if (board == null) {
            //TODO
        }
        board.setStatus(Status.valueOf(status));
        //TODO
        board = boardService.update(board);
        return ResponseEntity.ok().body(board);
    }

}
