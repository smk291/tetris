package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

class OPiece extends Tetromino {
    private final static int[][][] shape =
            {{
                    {0, -1}, {1, -1},
                    {0, 0}, {1, 0}
            }};
    private final static int orientations = 1;

    OPiece() {
        super(shape, orientations);
    }
}
