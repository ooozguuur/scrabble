package com.gunes.model.vo;

import java.util.ArrayList;
import java.util.List;

public class MoveVO extends IdBaseVO {

    private int sequence;

    private List<WordVO> words = new ArrayList<>();

    public int getSequence() {
        return sequence;
    }

    public void setSequence(final int sequence) {
        this.sequence = sequence;
    }

    public List<WordVO> getWords() {
        return words;
    }

    public void setWords(final List<WordVO> words) {
        this.words = words;
    }
}
