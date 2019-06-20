package com.newtetris.tetrispiece.pieces;

public class LPiece extends Tetromino {
    private final static int[][][] shapeByRotation =
            {
                    {
                            {-1, 0},/*{0, 0},*/ {1, 0},
                            {-1, 1}
                    },
                    {
                            {0, -1},
                            /*{0,  0},*/
                            {0, 1}, {1, 1}
                    },
                    {
                            {1, -1},
                            {-1, 0},/*{0,  0},*/{1, 0}
                    },
                    {
                            {-1, -1}, {0, -1},
                            /*{0,  0},*/
                            {0, 1}
                    },
            };
    private final static int orientations = 4;

    LPiece() {
        super(shapeByRotation, orientations);
    }

}
