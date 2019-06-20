package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

class TPiece extends Tetromino {
    private final static int[][] shape =
            {
                    {-1, 0},/*{0, 0},*/{1, 0},
                              {0, 1}
            };
    private final static int orientations = 4;

    TPiece() {
        super(shape, orientations);
    }

}

