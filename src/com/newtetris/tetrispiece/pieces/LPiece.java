package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;

class LPiece extends Tetromino {
    private final static int[][][] shape =
            {
                                    {{1,-1},
                    {-1, 0}, {0, 0}, {1, 0}},

                    {{0,-1},
                     {0, 0},
                     {0, 1}, {1, 1}},

                    {{-1, 0}, {0, 0}, {1, 0},
                     {-1, 1}},

                    {{-1,-1},{0,-1},
                             {0, 0},
                             {0, 1}}

            };
    private final static int orientations = 4;

    LPiece() {
        super(shape, orientations);
    }

}
