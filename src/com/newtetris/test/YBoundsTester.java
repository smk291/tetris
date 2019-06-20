package com.newtetris.test;

import com.newtetris.playfield.Coords;

import java.util.Arrays;

public class YBoundsTester extends BoundsTester {
    private int maxY;
    private int minY;

    public YBoundsTester(int maxY) {
        this.maxY = maxY;
        this.minY = 0;
    }

    public boolean apply(Coords c) {
        return applyTest(minY, maxY, c.getY());
    }

    private boolean applyNoMin(Coords c) {
        return applyTest(-2, maxY, c.getY());
    }

    public boolean applyArrayNoMin(Coords[] c) {
        return Arrays.stream(c).allMatch(i -> new YBoundsTester(maxY).applyNoMin(i));
    }
}
