package com.gunes.model.entity;

import com.gunes.model.entity.base.IdBaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CELL")
public class Cell extends IdBaseEntity {

    @Column(name = "CHARACTER")
    private char character;

    @Column(name = "X_POSITION")
    private int xPosition;

    @Column(name = "Y_POSITION")
    private int yPosition;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public char getCharacter() {
        return character;
    }

    public void setCharacter(final char character) {
        this.character = character;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(final int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(final int yPosition) {
        this.yPosition = yPosition;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(final Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "character=" + character +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                "} ";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Cell cell = (Cell) o;
        return character == cell.character &&
                xPosition == cell.xPosition &&
                yPosition == cell.yPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, xPosition, yPosition);
    }
}
