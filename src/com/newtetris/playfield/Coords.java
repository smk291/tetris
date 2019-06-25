package com.newtetris.playfield;

public class Coords {
    private int x;
    private int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coords(int[] coords) {
        this.x = coords[0];
        this.y = coords[1];
    }

    public Coords clone() {
        return new Coords(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coords sum(Coords bc) {
        return new Coords(this.x + bc.getX(), this.y + bc.getY());
    }

    public Coords sum(int x, int y) {
        return new Coords(this.x + x, this.y + y);
    }

    public Coords sum(int[] xAndY) {
        return new Coords(this.x + xAndY[0], this.y + xAndY[1]);
    }

    public void mutateSum(Integer[] xAndY) {
        this.x += xAndY[0];
        this.y += xAndY[1];
    }

    public void mutateSum(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void mutateSum() {
        this.x += x;
        this.y += y;
    }

    public Coords negate() {
        return new Coords(-this.x, -this.y);
    }
}
