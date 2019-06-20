package com.newtetris.test;

import com.newtetris.playfield.Coords;

import java.util.Arrays;

public class YBoundsTester extends BoundsTester {
    private int maxY;
    private int minY;

//    YBoundsTester(int maxY, int minY) {
//        this.maxY = maxY;
//        this.minY = minY;
//    }

    public YBoundsTester(int maxY) {
        this.maxY = maxY;
        this.minY = 0;
    }

    public boolean apply(Coords c) {
        return applyTest(minY, maxY, c.getY());
    }

    public boolean applyNoMin(Coords c) {
        return applyTest(-2, maxY, c.getY());
    }

//    public boolean applyArray(Coords[] c) {
//        return Arrays.stream(c).allMatch(i -> new YBoundsTester(maxY).apply(i));
//    }

    public boolean applyArrayNoMin(Coords[] c) {
        return Arrays.stream(c).allMatch(i -> new YBoundsTester(maxY).applyNoMin(i));
    }
}
