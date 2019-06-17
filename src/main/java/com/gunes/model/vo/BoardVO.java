package com.gunes.model.vo;

import com.gunes.enums.Status;

public class BoardVO extends IdBaseVO {

    private Status status;

    private int horizontalSize;

    private int verticalSize;


    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public int getHorizontalSize() {
        return horizontalSize;
    }

    public void setHorizontalSize(final int horizontalSize) {
        this.horizontalSize = horizontalSize;
    }

    public int getVerticalSize() {
        return verticalSize;
    }

    public void setVerticalSize(final int verticalSize) {
        this.verticalSize = verticalSize;
    }


    @Override
    public String toString() {
        return "BoardVO{" +
                "status=" + status +
                ", horizontalSize=" + horizontalSize +
                ", verticalSize=" + verticalSize +
                "} " + super.toString();
    }
}
