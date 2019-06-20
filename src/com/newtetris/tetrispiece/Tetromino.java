package com.newtetris.tetrispiece;

import com.newtetris.playfield.Coords;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

    public Coords[] getShapeByRotation(int rotations) {
        Coords[] coords = new Coords[4];

        switch (rotations) {
            case 1: // 90 -- flip x & y, negate new y
                IntStream.range(0, shape.length).forEach(i -> coords[i] = new Coords(shape[i][1], -shape[i][0]));

                break;
            case 2: // 180 -- negate both
                IntStream.range(0, shape.length).forEach(i -> coords[i] = new Coords(-shape[i][0], -shape[i][1]));

                break;
            case 3: // 270 -- flip x & y, negate new x
                IntStream.range(0, shape.length).forEach(i -> coords[i] = new Coords(-shape[i][1], shape[i][0]));

                break;
            case 0:
            default:
                IntStream.range(0, shape.length).forEach(i -> coords[i] = new Coords(shape[i]));

                break;
        }

        coords[3] = new Coords(0, 0);

        return coords;
    }

    public int getUniqueOrientations() {
        return this.orientations;
    }

    public int getPieceSize() {
        return this.pieceSize;
    }
}