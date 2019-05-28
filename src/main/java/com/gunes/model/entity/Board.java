package com.gunes.model.entity;

import com.gunes.enums.Status;
import com.gunes.model.entity.base.IdBaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BOARD")
public class Board extends IdBaseEntity implements Serializable {

    private static final int DEFAULT_HORIZONTAL_SIZE = 15;

    private static final int DEFAULT_VERTICAL_SIZE = 15;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

    @Column(name = "HORIZONTAL_SIZE")
    private int horizontalSize = DEFAULT_HORIZONTAL_SIZE;

    @Column(name = "VERTICAL_SIZE")
    private int verticalSize = DEFAULT_VERTICAL_SIZE;

    public Board() {
    }

    public Board(final int horizontalSize, final int verticalSize) {
        this.horizontalSize = horizontalSize;
        this.verticalSize = verticalSize;
    }

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
        return "Board{" +
                "status=" + status +
                ", horizontalSize=" + horizontalSize +
                ", verticalSize=" + verticalSize +
                "} ";
    }
}
