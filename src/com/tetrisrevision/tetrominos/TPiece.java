package com.tetrisrevision.tetrominos;

import java.awt.*;

class TPiece extends Tetromino {
  private static final int[][][] offsets = {
    {{0, -1}, {-1, 0}, {0, 0}, {1, 0}},
    {{0, -1}, {0, 0}, {1, 0}, {0, 1}},
    {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
    {{0, -1}, {-1, 0}, {0, 0}, {0, 1}}
  };

  TPiece() {
    super.setOffsets(offsets);
    super.setColor(Color.green);
  }
}
