package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

public class OPiece extends Tetromino {
    private final static int[][] shape =
            {
                    {-1, 0}, /*{0, 0},*/
                    {-1, 1}, {0, 1}
            };
    private final static int orientations = 1;

    OPiece() {
        super(shape, orientations);

    }
}
