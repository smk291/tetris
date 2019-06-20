package com.newtetris.tetrispiece.rotate;

import com.newtetris.tetrispiece.Manipulator;
import com.newtetris.tetrispiece.TetrisPiece;

abstract public class Rotate extends Manipulator {
    public static void applyRotate (TetrisPiece t, int increment) {
        int uniqueOrientations = t.getUniqueOrientations();
        int newOrientation = t.getOrientation() + increment;

        if (newOrientation < 0) {
            newOrientation = uniqueOrientations - 1;
        } else if (newOrientation >= uniqueOrientations) {
            newOrientation = 0;
        }

        t.setOrientation(newOrientation);
    }

    abstract public void apply (TetrisPiece t);
}