package com.tetrisrevision.tetrominos;

import java.awt.*;

class ZPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{-1, -1}, {0, -1}, {0, 0}, {1, 0}},
    {{1, -1}, {0, 0}, {1, 0}, {0, 1}},
    {{-1, 0}, {0, 0}, {0, 1}, {1, 1}},
    {{0, -1}, {-1, 0}, {0, 0}, {-1, 1}}
  };

  ZPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.black);
  }
}
