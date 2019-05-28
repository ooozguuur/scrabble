package com.gunes.service;

import com.gunes.model.entity.Board;
import com.gunes.service.base.GenericService;

public interface BoardService extends GenericService<Board, Long> {

    Board createBoard();

    Board getById(Long boardId);

    Board update(Board board);
}
