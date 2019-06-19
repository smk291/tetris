package com.newtetris.tetrispiece;

abstract public class RotationIncrementer {
    public static void apply(TetrisPiece t, int increment) {
        int rotationCount = t.getOrientationsCount();
        int newRotations = t.getOrientation() + increment;

        if (newRotations < 0) {
            newRotations = rotationCount - 1;
        } else if (newRotations >= rotationCount) {
            newRotations = 0;
        }

        t.setOrientation(newRotations);
    }

    public static void left (TetrisPiece t) {
        apply(t, 1);
    }

    public static void right (TetrisPiece t) {
        apply(t, -1);
    }
}
