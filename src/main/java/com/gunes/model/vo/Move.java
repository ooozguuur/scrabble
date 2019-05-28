package com.gunes.model.vo;

import com.gunes.enums.DirectionType;

import java.io.Serializable;

public class Move implements Serializable {

    private String letters;

    private int horizontalStartingPoint;

    private int verticalStartingPoint;

    private DirectionType directionType;

    public String getLetters() {
        return letters;
    }

    public void setLetters(final String letters) {
        this.letters = letters;
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

    @Override
    public String toString() {
        return "Move{" +
                "letters='" + letters + '\'' +
                ", horizontalStartingPoint=" + horizontalStartingPoint +
                ", verticalStartingPoint=" + verticalStartingPoint +
                ", directionType=" + directionType +
                '}';
    }
}
