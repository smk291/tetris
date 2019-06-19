package com.newtetris.playfield;

public class Cell extends Coords {
    private boolean empty = true;

    public Cell(int x, int y) {
        super(x, y);
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
