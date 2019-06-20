package com.gunes.service;

import com.gunes.model.entity.Board;
import com.gunes.service.base.GenericService;

public interface BoardService extends GenericService<Board> {

    Board createBoard();

}
