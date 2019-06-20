package com.newtetris.tetrispiece.pieces;

class TPiece extends Tetromino {
    private final static int[][][] shapeByRotation =
            {
                    {
                            {-1, 0},/*{0,  0},*/{1, 0},
                            {0, 1}
                    },
                    {
                            {0, -1},
                            /*{0,  0},*/{1, 0},
                            {0, 1}
                    },
                    {
                            {0, -1},
                            {-1, 0},/*{0,  0},*/ {1, 0}
                    },
                    {
                            {0, -1},
                            {-1, 0},/*{0,  0},*/
                            {0, 1}
                    }
            };
    private final static int orientations = 4;

    TPiece() {
        super(shapeByRotation, orientations);
    }

}

