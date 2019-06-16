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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/move", consumes = "application/json", produces = "application/json")
public class MoveController {

    private static final MoveMapper mapper = Mappers.getMapper(MoveMapper.class);

    @Autowired
    private BoardService boardService;

    private final MoveService moveService;

    public MoveController(final MoveService moveService) {
        this.moveService = moveService;
    }

    @GetMapping("get-board-content/{boardId}/{sequence}")
    public ResponseEntity<MoveVO> getBoardContent(@PathVariable Long boardId, @PathVariable Integer sequence) {
        Board board = boardService.getById(boardId);
        if (board == null) {
            throw new BoardNotFoundException("Board not found. Id:{} " + boardId);
        }
        if (board.getStatus().equals(Status.PASSIVE)) {
            throw new BoardNotActiveException("Board not active. Id:{} " + boardId);
        }
        Move move = moveService.getBoardContent(board, sequence);
        return ResponseEntity.ok().body(mapper.mapToVO(move));
    }

}
