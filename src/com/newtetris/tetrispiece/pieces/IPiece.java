package com.newtetris.tetrispiece.pieces;

public class IPiece extends Tetromino {
    private final static int[][][] shapeByRotation =
            {
                    {
                            {-2, 0}, {-1, 0}, /* {0, 0},*/ {1, 0}
                    },
                    {
                            {0, -1},
                            /* {0, 0}, */
                            {0,  1},
                            {0,  2}
                    }
            };
    private final static int orientations = 2;

    public IPiece() {
        super(shapeByRotation, orientations);
    }

}
