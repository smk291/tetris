package com.tetrisrevision.tetrominos;

class SPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{0, -1}, {1, -1}, {-1, 0}, {0, 0}},
    {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
    {{0, 0}, {1, 0}, {-1, 1}, {0, 1}},
    {{-1, -1}, {-1, 0}, {0, 0}, {0, 1}}
  };

  SPiece() {
    super.setOffsets(offsets);
  }
}
