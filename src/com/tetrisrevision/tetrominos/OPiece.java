package com.tetrisrevision.tetrominos;

class OPiece extends Tetromino {
    private final static int[][][] offsets = {{{0, -1}, {1, -1}, {0, 0}, {1, 0}}};

    OPiece() {
        super.setOffsets(offsets);
    }
}
