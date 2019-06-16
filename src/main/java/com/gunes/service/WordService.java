package com.gunes.service;

import com.gunes.enums.DirectionType;
import com.gunes.model.entity.Board;
import com.gunes.model.entity.Word;
import com.gunes.service.base.GenericService;

import java.util.List;

public interface WordService extends GenericService<Word, Long> {

    List<Word> createWordsByBoard(Board board, Word word);

    default boolean isAvailableCoordinates(int horizontalSize, int verticalSize, Word word) {
        if (word.getHorizontalStartingPoint() < 0 || word.getHorizontalStartingPoint() > horizontalSize) {
            return false;
        }

        if (word.getVerticalStartingPoint() < 0 || word.getVerticalStartingPoint() > verticalSize) {
            return false;
        }

        if (word.getDirectionType() == DirectionType.HORIZONTAL && (word.getHorizontalStartingPoint() + word.getLetters().length()) > horizontalSize) {
            return false;
        }

        if (word.getDirectionType() == DirectionType.VERTICAL && (word.getVerticalStartingPoint() + word.getLetters().length()) > verticalSize) {
            return false;
        }
        return true;
    }

    List<Word> getWords(Long boardId);
}
