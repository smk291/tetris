package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

public class SPiece extends Tetromino {
    private final static int[][] shape =
            {
                           /*{0, 0},*/{1, 0},
                    {-1, 1}, {0, 1}
            };
    private final static int orientations = 2;

    SPiece() {
        super(shape, orientations);
    }
}


