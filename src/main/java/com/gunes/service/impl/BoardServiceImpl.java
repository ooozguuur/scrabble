package com.gunes.service.impl;

import com.gunes.dao.BoardDao;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import com.gunes.service.MoveService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl extends GenericServiceImpl<Board, Long> implements BoardService {

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
        Board board = boardDao.createEntityObject();
        board =  boardDao.save(board);
        moveService.firstMoveByBoard(board);
        return board;
    }

    @Override
    public Board getById(final Long boardId) {
        return boardDao.findById(boardId);
    }

    @Override
    public Board update(final Board board) {
        return boardDao.update(board);
    }


}
