package com.newtetris.playfield;

import com.newtetris.test.XBoundsTester;
import com.newtetris.test.YBoundsTester;

public class Cell extends Coords {
    private boolean empty = true;

    public Cell(int x, int y) {
        super(x, y);
    }

    public static boolean isValidCell(Coords coords) {
        return new XBoundsTester().apply(coords) && new YBoundsTester().apply(coords);
    }

    public void setEmpty() {
        this.empty = true;
    }

    public void setFull() {
        this.empty = false;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isFull() {
        return !this.empty;
    }
}
