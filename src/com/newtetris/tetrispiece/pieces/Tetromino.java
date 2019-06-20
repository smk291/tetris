package com.newtetris.tetrispiece.pieces;

import com.newtetris.playfield.Coords;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tetromino {
    // Piece orientation and rotation referenec:
    // https://www.colinfahey.com/tetris/tetris.html
    private final int[][][] shapeByRotation;
    private final int orientations;
    private final int pieceSize = 4;

    Tetromino(int[][][] shapeByRotation, int orientations) {
        this.shapeByRotation = shapeByRotation;
        this.orientations = orientations;
    }

    public Coords[] getShapeByRotation(int rotations) {
        List<Coords> list =
                Arrays.stream(shapeByRotation[rotations])
                        .map(Coords::new)
                        .collect(Collectors.toList());

        list.add(new Coords(0, 0));

        return list.toArray(new Coords[0]);
    }

    public int getUniqueOrientations() {
        return this.orientations;
    }

    public int getPieceSize() {
        return this.pieceSize;
    }
}