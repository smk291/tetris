package com.newtetris;

public class Coords {
    private int x;
    private int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coords sum(Coords bc) {
        return new Coords(this.x + bc.getX(), this.y + bc.getY());
    }

    public Coords sum(int x, int y) {
        return new Coords(this.x + x, this.y + y);
    }
}
