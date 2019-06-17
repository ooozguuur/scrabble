package com.gunes.controller;

import com.gunes.exceptions.BoardNotFoundException;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Cell;
import com.gunes.model.entity.Move;
import com.gunes.model.vo.CellVO;
import com.gunes.model.vo.mapper.CellMapper;
import com.gunes.service.BoardService;
import com.gunes.service.MoveService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/move", produces = "application/json")
public class MoveController {

    private static final CellMapper mapper = Mappers.getMapper(CellMapper.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveController.class);

    @Autowired
    private BoardService boardService;

    private final MoveService moveService;

    public MoveController(final MoveService moveService) {
        this.moveService = moveService;
    }

    @GetMapping("/get-board-content/{boardId}/{sequence}")
    public @ResponseBody ResponseEntity<List<CellVO>> getBoardContent(@PathVariable Long boardId, @PathVariable Integer sequence) {
        Board board = boardService.findById(boardId);
        if (board == null) {
            LOGGER.error("Board not found. {}", boardId);
            throw new BoardNotFoundException("Board not found. Id:{} " + boardId);
        }
        List<Move> moves = moveService.getBoardContent(board, sequence);
        Set<Cell> cells = new HashSet<>();
        moves.forEach(move -> move.getWords().forEach(word -> cells.addAll(word.getCells())));
        List<CellVO> cellVOS = cells.stream().map(mapper::mapToVO).collect(Collectors.toList());
        return ResponseEntity.ok().body(cellVOS);
    }

}
