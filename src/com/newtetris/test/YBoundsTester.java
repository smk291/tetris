package com.newtetris.test;

import com.newtetris.playfield.Coords;

import java.util.Arrays;

public class YBoundsTester extends BoundsTester {
    public static int height;
    private int maxY;
    private int minY;

    public YBoundsTester() {
        this.maxY = height;
        this.minY = 0;
    }

    public static void setHeight(int height) {
        YBoundsTester.height = height;
    }

    public boolean apply(Coords c) {
        return applyTest(minY, maxY, c.getY());
    }

    private boolean applyNoMin(Coords c) {
        return applyTest(-2, maxY, c.getY());
    }

    public boolean applyArrayNoMin(Coords[] c) {
        return Arrays.stream(c).allMatch(i -> new YBoundsTester().applyNoMin(i));
    }
}
