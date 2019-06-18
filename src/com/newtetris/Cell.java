package com.newtetris;

public class Cell extends Coords {
    private boolean empty = true;

    Cell(int x, int y) {
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
