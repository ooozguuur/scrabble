package com.gunes.model.vo;

import com.gunes.model.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class MoveVO extends IdBaseVO {

    private int sequence;

    private List<Word> words = new ArrayList<>();

    public int getSequence() {
        return sequence;
    }

    public void setSequence(final int sequence) {
        this.sequence = sequence;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(final List<Word> words) {
        this.words = words;
    }
}
