package com.newtetris.tetrispiece.pieces;

public class ZPiece extends Tetromino {
    private final static int[][][] shapeByRotation =
            {
                    {
                            {-1, 0},/*{0,  0},*/
                            {0, 1}, {1, 1}
                    },
                    {
                            {1, -1},
                            /*{0, 0},*/{1, 0},
                            {0, 1}
                    }
            };
    private final static int orientations = 2;

    ZPiece() {
        super(shapeByRotation, orientations);
    }
}
