package com.gunes.service.impl;

import com.gunes.dao.BoardDao;
import com.gunes.model.entity.Board;
import com.gunes.service.BoardService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl extends GenericServiceImpl<Board, Long> implements BoardService {

    private BoardDao boardDao;

    @Autowired
    public BoardServiceImpl(final BoardDao boardDao) {
        super(boardDao);
        this.boardDao = boardDao;
    }

    @Override
    public Board createBoard() {
        Board board = boardDao.createEntityObject();
        return boardDao.save(board);
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
