package com.newtetris.tetrispiece;

import com.newtetris.Coords;

abstract public class SpawnOrientationToPlayField {
    public static Coords[] apply(TetrisPiece t) {
        int pieceSize = t.getPieceSize();
        Coords center = t.getCenter();
        Coords[] templateOffsets = t.getShape();
        Coords[] newInsertionCoords = new Coords[pieceSize];

        for (int i = 0; i < pieceSize; i++) {
            newInsertionCoords[i] = templateOffsets[i].sum(center);
        }

        return newInsertionCoords;
    }
}
