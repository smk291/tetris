package com.newtetris.tetrispiece;

import com.newtetris.coords.Coords;

abstract public class SpawnOrientation {
    public static Coords[] apply(TetrisPiece t) {
        return t.getTetromino().getShapeByRotation(t.getOrientation());
    }
}
