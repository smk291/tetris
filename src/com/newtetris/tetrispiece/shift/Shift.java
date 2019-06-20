package com.newtetris.tetrispiece.shift;

import com.newtetris.playfield.Coords;
import com.newtetris.tetrispiece.GetInsertionCoordinates;
import com.newtetris.tetrispiece.Manipulator;
import com.newtetris.tetrispiece.TetrisPiece;

public class Shift extends Manipulator {
    static void applyShift(TetrisPiece t, Coords offset) {
        t.setCenter(t.getCenter().sum(offset));
    }

    public void apply(TetrisPiece t) {
    }
}