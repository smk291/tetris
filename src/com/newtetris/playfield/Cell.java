package com.newtetris.playfield;

import com.newtetris.test.XBoundsTester;
import com.newtetris.test.YBoundsTester;

public class Cell extends Coords {
    public static int width = 10;
    public static int height = 24;
    private boolean empty = true;

    public Cell(int x, int y) {
        super(x, y);
    }

    public static boolean isValidCell(Coords coords) {
        return new XBoundsTester(width).apply(coords) && new YBoundsTester(height).apply(coords);
    }

//    public static boolean isValidFallingCell(Coords coords) {
//        return new XBoundsTester(width).apply(coords) && new YBoundsTester(height).applyNoMin(coords);
//    }

    public static void setWidthAndHeight(int width, int height) {
        Cell.width = width;
        Cell.height = height;
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
