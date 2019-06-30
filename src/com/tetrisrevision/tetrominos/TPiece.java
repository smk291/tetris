package com.tetrisrevision.tetrominos;

class TPiece extends Tetromino {
    private final static int[][][] offsets =
            {
                    {{0, -1}, {-1, 0}, {0, 0}, {1, 0}},
                    {{0, -1}, {0, 0}, {1, 0}, {0, 1}},
                    {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                    {{0, -1}, {-1, 0}, {0, 0}, {0, 1}}
            };

    TPiece() {
        super.setOffsets(offsets);
    }

}

