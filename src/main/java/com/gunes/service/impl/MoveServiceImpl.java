package com.gunes.service.impl;

import com.gunes.dao.MoveDao;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Move;
import com.gunes.model.entity.Word;
import com.gunes.service.MoveService;
import com.gunes.service.WordService;
import com.gunes.service.base.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoveServiceImpl extends GenericServiceImpl<Move, Long> implements MoveService {

    @Autowired
    private WordService wordService;

    private MoveDao moveDao;

    @Autowired
    public MoveServiceImpl(final MoveDao moveDao) {
        super(moveDao);
        this.moveDao = moveDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void play(final Board board, final Move move) {
        int sequence = this.getLastMoveSequenceByBoardId(board.getId());
        move.setSequence(++sequence);
        move.setBoard(board);
        for (Word wordVO : move.getWords()) {
            List<Word> words = wordService.createWordsByBoard(board, wordVO);
            move.getWords().addAll(words);
        }
        this.save(move);
    }

    private int getLastMoveSequenceByBoardId(long boardId) {
        return moveDao.getLastMoveSequenceByBoardId(boardId);
    }

    @Override
    public Move save(final Move move) {
        return moveDao.save(move);
    }

    @Override
    public void firstMoveByBoard(final Board board) {
        Move move = this.createEntityObject();
        move.setBoard(board);
        this.save(move);
    }


}
