package com.gunes.model.entity;

import com.gunes.model.entity.base.IdBaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MOVE")
public class Move extends IdBaseEntity {

    @Column(name = "SEQ")
    private int sequence;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Word.class)
    private Set<Word> words;

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

    @Override
    public String toString() {
        return "Move{" +
                "sequence=" + sequence +
                ", words=" + words +
                "} " + super.toString();
    }
}
