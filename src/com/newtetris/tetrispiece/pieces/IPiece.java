package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

public class IPiece extends Tetromino {
    private final static int[][] shape =
            {
                    {-2, 0}, {-1, 0}, /* {0, 0},*/ {1, 0}
            };
    private final static int orientations = 2;

    public IPiece() {
        super(shape, orientations);
    }

}
