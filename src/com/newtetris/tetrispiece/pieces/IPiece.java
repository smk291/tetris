package com.newtetris.tetrispiece.pieces;

import com.newtetris.tetrispiece.Tetromino;
import com.newtetris.tetrispiece.kick.KickIPiece;

public class IPiece extends Tetromino {
    private final static int[][][] shape = {

            {{-1, 0}, {0, 0}, {1, 0}, {2, 0}},

                                 {{1, -1},
                                  {1, 0},
                                  {1, 1},
                                  {1, 2}},

            {{-1, 1}, {0, 1}, {1, 1}, {2, 1}},

                     {{0, -1},
                      {0, 0},
                      {0, 1},
                      {0, 2}},

    };
    private final static int orientations = 4;

    public IPiece() {
        super(shape, orientations);
    }

    public KickIPiece kick(){
        return new KickIPiece();
    }
}
