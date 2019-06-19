package com.newtetris.tetrispiece;

import com.newtetris.Coords;

abstract public class SpawnOrientation {
    public static Coords[] apply(TetrisPiece t) {
        return t.getTetromino().getTemplateOffsetsByRotation(t.getRotation());
    }
}
