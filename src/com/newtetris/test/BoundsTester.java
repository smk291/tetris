package com.newtetris.test;

import com.newtetris.playfield.Coords;

public abstract class BoundsTester {
    public boolean applyTest(int min, int max, int current) {
        return current >= min && current < max;
    }
}

