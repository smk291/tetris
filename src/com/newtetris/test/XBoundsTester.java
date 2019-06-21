package com.newtetris.test;

import com.newtetris.playfield.Coords;

import java.util.Arrays;

public class XBoundsTester extends BoundsTester {
    private static int width;
    private int maxX;
    private int minX;

    public XBoundsTester() {
        this.maxX = width;
        minX = 0;
    }

    public static void setWidth (int width) {
        XBoundsTester.width = width;
    }

    public boolean apply(Coords c) {
        return applyTest(minX, maxX, c.getX());
    }

    public boolean applyArray(Coords[] c) {
        return Arrays.stream(c).allMatch(i -> apply(i));
    }
}
