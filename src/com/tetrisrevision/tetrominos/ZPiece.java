package com.tetrisrevision.tetrominos;

class ZPiece extends Tetromino {
    private final static int[][][] offsets =
            {
                    {{-1, -1}, {0, -1}, {0, 0}, {1, 0}},
                    {{1, -1}, {0, 0}, {1, 0}, {0, 1}},
                    {{-1, 0}, {0, 0}, {0, 1}, {1, 1}},
                    {{0, -1}, {-1, 0}, {0, 0}, {-1, 1}}
            };

    ZPiece() {
        super.setOffsets(offsets);
    }
}