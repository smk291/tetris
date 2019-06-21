package com.newtetris.tetrispiece;

import com.newtetris.playfield.Coords;

import java.util.stream.IntStream;

public class Tetromino {
    // Piece orientation and rotation reference: https://www.colinfahey.com/tetris/tetris.html
    private final int[][] shape;
    private final int orientations;
    private final int pieceSize = 4;

    public Tetromino(int[][] shape, int orientations) {
        this.shape = shape;
        this.orientations = orientations;
    }

    Coords[] getShapeByRotation(int rotations) {
        Coords[] coords = new Coords[pieceSize];

        switch (rotations) {
            case 1: // 90 -- flip x & y, negate new y
                int bound = shape.length;
                for (int i1 = 0; i1 < bound; i1++) {
                    coords[i1] = new Coords(shape[i1][1], -shape[i1][0]);
                }

                break;
            case 2: // 180 -- negate both
                int bound1 = shape.length;
                for (int i1 = 0; i1 < bound1; i1++) {
                    coords[i1] = new Coords(-shape[i1][0], -shape[i1][1]);
                }

                break;
            case 3: // 270 -- flip x & y, negate new x
                int bound2 = shape.length;
                for (int i1 = 0; i1 < bound2; i1++) {
                    coords[i1] = new Coords(-shape[i1][1], shape[i1][0]);
                }

                break;
            case 0:
            default:
                int bound3 = shape.length;
                for (int i = 0; i < bound3; i++) {
                    coords[i] = new Coords(shape[i]);
                }

                break;
        }

        coords[3] = new Coords(0, 0);

        return coords;
    }

    int getUniqueOrientations() {
        return this.orientations;
    }

    int getPieceSize() {
        return this.pieceSize;
    }
}