package com.tetrisrevision.tetrominos;

class LPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{1, -1}, {-1, 0}, {0, 0}, {1, 0}},
    {{0, -1}, {0, 0}, {0, 1}, {1, 1}},
    {{-1, 0}, {0, 0}, {1, 0}, {-1, 1}},
    {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}
  };

  LPiece() {
    super.setOffsets(offsets);
  }
}
