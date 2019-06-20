package com.newtetris.tetrispiece.pieces;

public class SPiece extends Tetromino {
    private final static int[][][] shapeByRotation =
            {
                    {
                            /*{0,  0},*/{1, 0},
                            {-1, 1}, {0, 1}
                    },
                    {
                            {0, -1},
                            /* {0,  0},*/ {1, 0},
                            {1, 1}
                    }
            };
    private final static int orientations = 2;

    SPiece() {
        super(shapeByRotation, orientations);
    }
}


