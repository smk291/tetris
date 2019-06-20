package com.newtetris.tetrispiece.pieces;

public class OPiece extends Tetromino {
    private final static int[][][] shapeByRotation =
            {
                    {
                            {-1, 0}, /*{0, 0},*/
                            {-1, 1}, {0, 1}
                    }
            };
    private final static int orientations = 1;

    OPiece() {
        super(shapeByRotation, orientations);

    }
}
