package com.newtetris.test;

import com.newtetris.playfield.Coords;

public abstract class BoundsTesters {
    private final static int width = 10;
    private final static int height = 24;

    static boolean left(Coords c) {
        return c.getX() > -1;
    }

    static boolean right(Coords c) {
        return c.getX() < width;
    }

    static boolean y(Coords c) {
        return c.getY() < height && c.getY() > -1;
    }
}
