package com.gunes.controller;

import com.gunes.enums.Status;
import com.gunes.exceptions.BoardNotFoundException;
import com.gunes.model.entity.Board;
import com.gunes.model.vo.BoardVO;
import com.gunes.model.vo.mapper.BoardMapper;
import com.gunes.service.BoardService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/board", produces = "application/json")
public class BoardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    private static final BoardMapper mapper = Mappers.getMapper(BoardMapper.class);

    private final BoardService boardService;

    public BoardController(final BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<Long> createBoard() {
        Board board = boardService.createBoard();
        LOGGER.info("Board created {}", board.getId());
        return ResponseEntity.ok().body(board.getId());
    }

    @PutMapping("/update-status/{boardId}")
    public @ResponseBody ResponseEntity<BoardVO> updateStatus(@PathVariable Long boardId, @RequestParam String status) {
        Board board = boardService.findById(boardId);
        if (board == null) {
            LOGGER.error("Board not found. {}", boardId);
            throw new BoardNotFoundException("Board not found. Id:{} " + boardId);
        }
        if (!Status.isValidStatus(status) || board.getStatus() == Status.PASSIVE) {
            LOGGER.error("The 'status' parameter  not acceptable {}", status);
            throw new IllegalArgumentException("The 'status' parameter  not acceptable ");
        }
        board.setStatus(Status.valueOf(status));
        board = boardService.update(board);
        LOGGER.info("Board updated status. Id:{}", board.getId());
        return ResponseEntity.ok().body(mapper.mapToVO(board));
    }
}
