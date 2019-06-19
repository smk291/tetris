package com.newtetris.tetrispiece;

import com.newtetris.Coords;

abstract public class PieceShifter {
    public static void apply(TetrisPiece t, Coords offset) {
        t.setCenter(t.getCenter().sum(offset));

        SpawnOrientationToPlayField.apply(t);
    }

    public static void applyLeft(TetrisPiece t) {
        apply(t, new Coords(-1, 0));
    }

    public static void applyRight(TetrisPiece t) {
        apply(t, new Coords(1, 0));
    }

    public static void applyDown(TetrisPiece t) {
        apply(t, new Coords(0, 1));
    }
}
