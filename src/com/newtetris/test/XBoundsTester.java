package com.newtetris.test;

import com.newtetris.playfield.Coords;

import java.util.Arrays;

public class XBoundsTester extends BoundsTester {
    private int maxX;
    private int minX;

    public XBoundsTester(int maxX, int minX) {
        this.maxX = maxX;
        this.minX = minX;
    }

    public XBoundsTester() {
        maxX = 10;
        minX = 0;
    }

    public boolean apply(Coords c) {
        return applyTest(minX, maxX, c.getX());
    }

    public boolean applyArray(Coords[] c) {
        return Arrays.stream(c).allMatch(i -> new XBoundsTester().apply(i));
    }
}
