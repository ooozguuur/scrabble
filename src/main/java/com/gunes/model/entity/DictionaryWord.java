package com.gunes.model.entity;

import com.gunes.model.entity.base.IdBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DICTIONARY_WORD")
public class DictionaryWord extends IdBaseEntity {

    @Column(name = "WORD", nullable = false)
    private String word;


    public DictionaryWord() {
    }

    public DictionaryWord(final String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }
}