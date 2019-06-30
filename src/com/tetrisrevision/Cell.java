package com.tetrisrevision;

import java.awt.*;

public class Cell {
//    private Point pt;
    private boolean empty;
//    private Color c;

    Cell () {
        this.empty = true;
    }

//    Cell (int y, int x) {
//        this.empty = true;
//        this.pt = new Point(x, y);
//    }

    void setEmpty(boolean status) {
        this.empty = status;
    }

    boolean isEmpty() {
        return this.empty;
    }

    public boolean isFull() {
        return !this.empty;
    }
}
