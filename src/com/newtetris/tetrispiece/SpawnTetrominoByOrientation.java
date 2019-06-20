package com.newtetris.tetrispiece;

import com.newtetris.playfield.Coords;

abstract public class SpawnTetrominoByOrientation {
    public static Coords[] apply(TetrisPiece t) {
        return t.getTetromino().getShapeByRotation(t.getOrientation());
    }
}
