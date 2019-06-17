package com.gunes.model.entity;

import com.gunes.model.entity.base.IdBaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MOVE", indexes = {@Index(name = "MOVE_BOARD_ID_INDEX", columnList = "BOARD_ID")},
       uniqueConstraints = {@UniqueConstraint(columnNames = {"SEQ", "BOARD_ID"})})
public class Move extends IdBaseEntity {

    @Column(name = "SEQ")
    private int sequence;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Word.class)
    private Set<Word> words = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(final int sequence) {
        this.sequence = sequence;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(final Set<Word> words) {
        this.words = words;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(final Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Move{" +
                "sequence=" + sequence +
                ", words=" + words +
                ", board=" + board +
                "} " + super.toString();
    }
}
