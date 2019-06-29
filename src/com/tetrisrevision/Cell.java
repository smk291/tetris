package com.tetrisrevision;

import java.awt.*;

public class Cell {
    private static PlayField p;
    private Point pt;
    private boolean empty;
    private Color c;

    Cell (int y, int x) {
        this.empty = true;
        this.pt = new Point(x, y);
    }

    public static void setStaticVariables(PlayField p) {
        Cell.p = p;
    }

    public void setEmpty(boolean status) {
        this.empty = status;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isFull() {
        return !this.empty;
    }
}
