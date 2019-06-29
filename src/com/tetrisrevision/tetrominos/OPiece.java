package com.tetrisrevision.tetrominos;

import com.tetrisrevision.kickdata.KickData;

class OPiece extends Tetromino {
    private final static int[][][] offsets =
            {{
                    {0, -1}, {1, -1},
                    {0, 0}, {1, 0}
            }};
    private final static int orientations = 1;

    OPiece() {
        super(offsets, new KickData(), "OPiece");
    }
}
