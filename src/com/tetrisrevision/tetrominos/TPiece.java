package com.tetrisrevision.tetrominos;

import java.awt.*;

class TPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{0, -1}, {-1, 0}, {0, 0}, {1, 0}}, // 0 up
    {{0, -1}, {0, 0}, {1, 0}, {0, 1}}, // 1 right
    {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}, // 2 down
    {{0, -1}, {-1, 0}, {0, 0}, {0, 1}} // 3 left
  };

  TPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.magenta);
    super.setTPiece(true);
  }
}
