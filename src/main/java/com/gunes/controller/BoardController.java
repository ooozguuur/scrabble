package com.gunes.controller;

import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

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

        }
        Board board = boardService.getById(boardId);
        if (board == null) {

        }
        board.setStatus(Status.valueOf(status));
        board = boardService.update(board);
        return ResponseEntity.ok().body(board);
    }

}
