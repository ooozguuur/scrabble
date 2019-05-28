package com.gunes.model.entity;

import com.gunes.enums.DirectionType;
import com.gunes.model.entity.base.IdBaseEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "WORD")
public class Word extends IdBaseEntity {

    @Column(name = "WORD", nullable = false)
    private String word;

    @Column(name = "HORIZONTAL_STARTING_POINT", nullable = false)
    private int horizontalStartingPoint;

    @Column(name = "VERTICAL_STARTING_POINT", nullable = false)
    private int verticalStartingPoint;

    @Column(name = "SCORE", nullable = false)
    private int score;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIRECTION_TYPE", nullable = false)
    private DirectionType directionType;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Cell.class)
    private Set<Cell> cells;

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public int getHorizontalStartingPoint() {
        return horizontalStartingPoint;
    }

    public void setHorizontalStartingPoint(final int horizontalStartingPoint) {
        this.horizontalStartingPoint = horizontalStartingPoint;
    }

    public int getVerticalStartingPoint() {
        return verticalStartingPoint;
    }

    public void setVerticalStartingPoint(final int verticalStartingPoint) {
        this.verticalStartingPoint = verticalStartingPoint;
    }

    public DirectionType getDirectionType() {
        return directionType;
    }

    public void setDirectionType(final DirectionType directionType) {
        this.directionType = directionType;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void setCells(final Set<Cell> cells) {
        this.cells = cells;
    }


    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", horizontalStartingPoint=" + horizontalStartingPoint +
                ", verticalStartingPoint=" + verticalStartingPoint +
                ", score=" + score +
                ", directionType=" + directionType +
                ", cells=" + cells +
                "} ";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Word word1 = (Word) o;
        return horizontalStartingPoint == word1.horizontalStartingPoint &&
                verticalStartingPoint == word1.verticalStartingPoint &&
                score == word1.score &&
                Objects.equals(word, word1.word) &&
                directionType == word1.directionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, horizontalStartingPoint, verticalStartingPoint, score, directionType);
    }
}
