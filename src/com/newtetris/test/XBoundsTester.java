package com.newtetris.test;

import com.newtetris.playfield.Coords;

import java.util.Arrays;

public class XBoundsTester extends BoundsTester {
    private int maxX;
    private int minX;

    public XBoundsTester(int maxX) {
        this.maxX = maxX;
        minX = 0;
    }

    public boolean apply(Coords c) {
        return applyTest(minX, maxX, c.getX());
    }

    public boolean applyArray(Coords[] c) {
        return Arrays.stream(c).allMatch(i -> new XBoundsTester(maxX).apply(i));
    }
}
