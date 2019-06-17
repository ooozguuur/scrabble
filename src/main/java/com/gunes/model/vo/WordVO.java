package com.gunes.model.vo;

import com.gunes.enums.DirectionType;

public class WordVO extends IdBaseVO {

    private String letters;

    private int horizontalStartingPoint;

    private int verticalStartingPoint;

    private int score;

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

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public DirectionType getDirectionType() {
        return directionType;
    }

    public void setDirectionType(final DirectionType directionType) {
        this.directionType = directionType;
    }

    @Override
    public String toString() {
        return "WordVO{" +
                "letters='" + letters + '\'' +
                ", horizontalStartingPoint=" + horizontalStartingPoint +
                ", verticalStartingPoint=" + verticalStartingPoint +
                ", score=" + score +
                ", directionType=" + directionType +
                "} " + super.toString();
    }
}
