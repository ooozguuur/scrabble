package com.gunes.service;

import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.service.base.GenericService;

import java.util.List;

public interface MoveService extends GenericService<Move, Long> {

    Move play(Board board, Move move);

    Move firstMoveByBoard(Board board);

    List<Move> getBoardContent(Board board, Integer sequence);
}
