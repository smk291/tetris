package com.newtetris.tetrispiece;

abstract public class RotationIncrementer {
    public static void apply(TetrisPiece t, int increment) {
        int rotationCount = t.getRotationCount();
        int newRotations = t.getRotation() + increment;

        if (newRotations < 0) {
            newRotations = rotationCount - 1;
        } else if (newRotations >= rotationCount) {
            newRotations = 0;
        }

        t.setRotation(newRotations);
    }

    public static void left (TetrisPiece t) {
        apply(t, 1);
    }

    public static void right (TetrisPiece t) {
        apply(t, -1);
    }
}
