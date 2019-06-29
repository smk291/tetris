package com.tetrisrevision.tetrominos;

import com.tetrisrevision.kickdata.KickData;

class SPiece extends Tetromino {
    private final static int[][][] offsets =
            {
                            {{0,-1}, {1,-1},
                    {-1, 0}, {0, 0}},

                            {{0,-1},
                             {0, 0}, {1, 0},
                                     {1, 1}},

                            {{0, 0}, {1, 0},
                     {-1, 1},{0, 1}},

                    {{-1,-1},
                     {-1, 0},{0, 0},
                             {0, 1}}
            };

    SPiece() {
        super(offsets, new KickData(), "SPiece");
    }
}


