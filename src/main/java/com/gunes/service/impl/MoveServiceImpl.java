package com.gunes.service.impl;

import com.gunes.dao.MoveDao;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.service.MoveService;
import com.gunes.service.WordService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoveServiceImpl extends GenericServiceImpl<Move> implements MoveService {

    private final WordService wordService;

    private MoveDao moveDao;

    @Autowired
    public MoveServiceImpl(final MoveDao moveDao, final WordService wordService) {
        super(moveDao);
        this.moveDao = moveDao;
        this.wordService = wordService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Move play(final Board board, final Move move) {
        Integer sequence = moveDao.getLastSequenceByBoardId(board.getId());
        move.setSequence(++sequence);
        move.setBoard(board);
        move.getWords().stream().map(word -> wordService.createWordsByBoard(board, word)).forEach(move::setWords);
        return moveDao.persist(move);
    }

    @Override
    public Move firstMoveByBoard(final Board board) {
        Move move = this.createEntityObject();
        move.setBoard(board);
        return moveDao.persist(move);
    }


    @Override
    public List<Move> getBoardContent(final Board board, final Integer sequence) {
        return moveDao.getBoardContent(board.getId(), sequence);
    }
}
