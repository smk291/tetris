package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

class IPiece extends Tetromino {
    private final static int[][] shape =
            {
                    {-2, 0}, {-1, 0}, /* {0, 0},*/ {1, 0}
            };
    private final static int orientations = 2;

    IPiece() {
        super(shape, orientations);
    }

}
