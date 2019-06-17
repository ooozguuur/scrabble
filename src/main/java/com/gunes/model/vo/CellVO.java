package com.gunes.model.vo;

public class CellVO extends IdBaseVO {

    private char character;

    private int xPosition;

    private int yPosition;

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

    @Override
    public String toString() {
        return "CellVO{" +
                "character=" + character +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                "} " + super.toString();
    }
}
