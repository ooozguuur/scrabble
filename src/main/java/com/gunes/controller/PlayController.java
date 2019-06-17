package com.gunes.controller;

import com.gunes.enums.Status;
import com.gunes.exceptions.BoardNotActiveException;
import com.gunes.exceptions.BoardNotFoundException;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.model.vo.MoveVO;
import com.gunes.model.vo.mapper.MoveMapper;
import com.gunes.service.BoardService;
import com.gunes.service.MoveService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/play", produces = "application/json")
public class PlayController {

    private static final MoveMapper mapper = Mappers.getMapper(MoveMapper.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayController.class);

    private final BoardService boardService;

    private final MoveService moveService;

    public PlayController(final MoveService moveService, final BoardService boardService) {
        this.moveService = moveService;
        this.boardService = boardService;
    }

    @PostMapping("/{boardId}")
    public @ResponseBody ResponseEntity<MoveVO> play(@PathVariable Long boardId, @RequestBody MoveVO moveVO) {
        Board board = boardService.findById(boardId);
        if (board == null) {
            LOGGER.error("Board not found. {}", boardId);
            throw new BoardNotFoundException("Board not found. Id:{} " + boardId);
        }
        if (board.getStatus().equals(Status.PASSIVE)) {
            LOGGER.error("Board not active. Id:{}", boardId);
            throw new BoardNotActiveException("Board not active. Id:{} " + boardId);
        }
        Move move = moveService.play(board, mapper.mapToEntity(moveVO));
        LOGGER.info("Added {} words", board.getId());
        return ResponseEntity.ok().body(mapper.mapToVO(move));
    }
}
