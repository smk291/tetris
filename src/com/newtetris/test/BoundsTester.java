package com.newtetris.test;

abstract class BoundsTester {
    boolean applyTest(int min, int max, int current) {
        return current >= min && current < max;
    }
}

