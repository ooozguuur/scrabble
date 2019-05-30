package com.gunes.controller;

import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.service.BoardService;
import com.gunes.service.MoveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/play", consumes = "application/json", produces = "application/json")
public class PlayController {

    private final BoardService boardService;

    private final MoveService moveService;

    public PlayController(final MoveService moveService, final BoardService boardService) {
        this.moveService = moveService;
        this.boardService = boardService;
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<Long> play(@PathVariable Long boardId, Move move) {
        if (move == null) {
            //TODO
        }
        Board board = boardService.getById(boardId);
        if (board == null) {
            //TODO
        }
        if (board.getStatus().equals(Status.PASSIVE)) {
            //TODO
        }
        moveService.play(board, move);
        return null;
    }
}
