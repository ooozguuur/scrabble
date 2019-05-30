package com.gunes.service;

import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.service.base.GenericService;

public interface MoveService extends GenericService<Move, Long> {

    void play(Board board, Move moves);

    Move save(Move move);

    void firstMoveByBoard(Board board);
}
