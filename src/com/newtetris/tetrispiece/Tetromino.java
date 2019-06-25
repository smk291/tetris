package com.newtetris.tetrispiece;

import com.newtetris.playfield.Coords;
import com.newtetris.tetrispiece.kick.Kick;

import java.util.stream.IntStream;

public class Tetromino {
    // Piece orientation and rotation reference: https://www.colinfahey.com/tetris/tetris.html
    private final int[][][] shape;
    private final int orientations;
    private final int pieceSize = 4;

    public Tetromino(int[][][] shape, int orientations) {
        this.shape = shape;
        this.orientations = orientations;
    }

    Coords[] getShapeByRotation(int orientation) {
        Coords[] coords = new Coords[pieceSize];

        for (int i = 0; i < coords.length; i++) {
            coords[i] = new Coords(shape[orientation][i][0], shape[orientation][i][1]);
        }

        return coords;
    }

    int getUniqueOrientations() {
        return this.orientations;
    }

    int getPieceSize() {
        return this.pieceSize;
    }

    public Kick kick() {
        return new Kick();
    }
}