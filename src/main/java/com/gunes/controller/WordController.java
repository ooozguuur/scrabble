package com.gunes.controller;

import com.gunes.exceptions.BoardNotFoundException;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Word;
import com.gunes.model.vo.WordVO;
import com.gunes.model.vo.mapper.WordMapper;
import com.gunes.service.BoardService;
import com.gunes.service.WordService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/word", consumes = "application/json", produces = "application/json")
public class WordController {

    private static final WordMapper mapper = Mappers.getMapper(WordMapper.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(WordController.class);

    @Autowired
    private BoardService boardService;

    private final WordService wordService;

    public WordController(final WordService wordService) {
        this.wordService = wordService;
    }


    @GetMapping("get-words/{boardId}")
    public ResponseEntity<List<WordVO>> getWords(@PathVariable Long boardId) {
        Board board = boardService.getById(boardId);
        if (board == null) {
            throw new BoardNotFoundException("Board not found. Id:{} " + boardId);
        }
        List<Word> words = wordService.getWords(board.getId());
        LOGGER.info("The number of words on the board is {} .", words.size());
        List<WordVO> wordVOs = words.stream().map(mapper::mapToVO).collect(Collectors.toList());
        return ResponseEntity.ok().body(wordVOs);
    }

}
