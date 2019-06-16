package com.gunes.controller;

import com.gunes.enums.Status;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import com.gunes.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/word", consumes = "application/json", produces = "application/json")
public class WordController {

    @Autowired
    private BoardService boardService;

    private final WordService wordService;

    public WordController(final WordService wordService) {
        this.wordService = wordService;
    }


    @GetMapping("get-words/{boardId}")
    public ResponseEntity<Long> getWords(@PathVariable Long boardId) {
        Board board = boardService.getById(boardId);
        if (board == null) {
            //TODO
        }
        if (board.getStatus().equals(Status.PASSIVE)) {
            //TODO
        }
        wordService.getWords(board.getId());
        return null;
    }

}
