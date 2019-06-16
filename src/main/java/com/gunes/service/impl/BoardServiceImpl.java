package com.gunes.service.impl;

import com.gunes.dao.BoardDao;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import com.gunes.service.MoveService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl extends GenericServiceImpl<Board, Long> implements BoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

    @Autowired
    private MoveService moveService;

    private BoardDao boardDao;

    @Autowired
    public BoardServiceImpl(final BoardDao boardDao) {
        super(boardDao);
        this.boardDao = boardDao;
    }

    @Override
    @Transactional
    public Board createBoard() {
        Board board = this.createEntityObject();
        LOGGER.debug("Created board object");
        board =  this.persist(board);
        LOGGER.debug("New Board saved");
        moveService.firstMoveByBoard(board);
        LOGGER.debug("The board did first move. {}", board.getId());
        return board;
    }

}
