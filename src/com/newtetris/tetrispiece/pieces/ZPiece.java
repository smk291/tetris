package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

public class ZPiece extends Tetromino {
    private final static int[][] shape =
            {
                    {-1, 0},/*{0,  0},*/
                              {0, 1}, {1, 1}
            };
    private final static int orientations = 2;

    ZPiece() {
        super(shape, orientations);
    }
}