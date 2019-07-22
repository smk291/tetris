package com.tetrisrevision.tetrominos;

import java.awt.*;

class LPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{1, -1}, {-1, 0}, {0, 0}, {1, 0}},
    {{0, -1}, {0, 0}, {0, 1}, {1, 1}},
    {{-1, 0}, {0, 0}, {1, 0}, {-1, 1}},
    {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}
  };

  LPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.cyan);
  }
}
