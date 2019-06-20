package com.newtetris.test;

public abstract class BoundsTester {
    public boolean applyTest(int min, int max, int current) {
        return current >= min && current < max;
    }
}

