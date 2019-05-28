package com.gunes.controller;

import com.gunes.model.entity.Board;
import com.gunes.model.vo.Move;
import com.gunes.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/play")
public class PlayController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/boardId")
    public ResponseEntity<Long> play(@PathVariable Long boardId, List<Move> moves) {
        Board board = boardService.getById(boardId);
        if (board == null) {
            return ResponseEntity.ok().body(board.getId());
        }
        return null;
    }
}
