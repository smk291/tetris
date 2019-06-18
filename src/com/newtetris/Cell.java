package com.newtetris;

public class Cell extends Coords {
    private boolean empty = true;

    Cell(int x, int y) {
        super(x, y);
    }

    void setEmpty() {
        this.empty = true;
    }

    public void setFull() {
        this.empty = false;
    }

    boolean isEmpty() {
        return this.empty;
    }

    boolean isFull() {
        return !this.empty;
    }
}
