package com.gunes.service;

import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.service.base.GenericService;

import java.util.List;

public interface MoveService extends GenericService<Move, Long> {

    void play(Board board, Move moves);

    void firstMoveByBoard(Board board);

    List<Move> getBoardContent(Board board, Integer sequence);
}
